package com.wbiao.service.Impl;

import com.alibaba.fastjson.JSON;
import com.wbiao.goods.feignService.GoodsFeign;
import com.wbiao.mapper.GoodsSearchMapper;
import com.wbiao.search.pojo.SkuInfo;
import com.wbiao.service.SearchService;
import com.wbiao.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImp implements SearchService {
    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private GoodsSearchMapper goodsSearchMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     *  把sku数据导入es
     */
    @Override
    public void importData() {
        //创建索引库
        elasticsearchTemplate.createIndex(SkuInfo.class);
        //创建映射
        elasticsearchTemplate.putMapping(SkuInfo.class);

        ResultUtil resultUtil = goodsFeign.selectSkuByEnable();

        //把sku对象转换成skuinfo对象
        List<SkuInfo> skuinfoList = JSON.parseArray(JSON.toJSONString(resultUtil.getData()), SkuInfo.class);

        for(SkuInfo skuInfo: skuinfoList){
            Map<String,Object> specMap = JSON.parseObject(skuInfo.getOwnSpec(), Map.class);
            skuInfo.setSpecMap(specMap);
        }
        goodsSearchMapper.saveAll(skuinfoList);

    }

    @Override
    public Map<String, Object> SearchGoods(Map<String, String> searchMap) {
        //搜索条件
        NativeSearchQueryBuilder queryBuilder = buildBasicQuery(searchMap);

        //搜索查询
        Map<String, Object> resultMap = searchList(queryBuilder);

        //分组聚合查询
        Map<String, Object> groupMap = searchGroupList(queryBuilder, searchMap);
        resultMap.putAll(groupMap);

        return resultMap;
    }


    /**
     *  条件搜索
     * @param searchMap
     * @return
     */
    private NativeSearchQueryBuilder buildBasicQuery(Map<String, String> searchMap) {
        //创建搜索对象，封装搜索条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if(searchMap!=null && searchMap.size()>0){
            //根据关键词搜索
            if(!StringUtils.isEmpty(searchMap.get("keywords"))){
                //queryBuilder.withQuery(QueryBuilders.queryStringQuery(searchMap.get("keywords")).field("title"));
                boolQueryBuilder.must(QueryBuilders.queryStringQuery(searchMap.get("keywords")).field("title"));
            }

            //根据品牌搜索
            if(!StringUtils.isEmpty(searchMap.get("brand"))){
                boolQueryBuilder.must(QueryBuilders.termQuery("brand",searchMap.get("brand")));
            }

            //规格过滤实现
            for(Map.Entry<String,String> entry: searchMap.entrySet()){
                String key = entry.getKey();
                if(key.startsWith("spec_")){
                    String val = entry.getValue();
                    boolQueryBuilder.must(QueryBuilders.termQuery("specMap."+key.substring(5)+".keyword",val));
                }
            }

            //价格区间筛选
            String price = searchMap.get("price");
            if(!StringUtils.isEmpty(price)){
                price = price.replace(",", "").replace("以下","").replace("以上", "");
                String[] prices = price.split("-");
                if(prices!=null && prices.length >0){
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gte(Integer.parseInt(prices[0])));
                    if(prices.length >1){
                        boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.parseInt(prices[1])));
                    }
                }
            }


            //排序
            String sortField = searchMap.get("sortField");
            String sortRule = searchMap.get("sortRule");
            if(!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)){
                queryBuilder.withSort(new FieldSortBuilder(sortField) //指定排序域
                        .order(SortOrder.valueOf(sortRule))); //指定排序规则
            }
        }

        //分页查询,如果用户不传分页参数默认第一页
        Integer pageNumber = coverterPage(searchMap);
        Integer size = 4;
        //es的分页默认是从0开始的
        queryBuilder.withPageable(PageRequest.of(pageNumber-1,size));

        queryBuilder.withQuery(boolQueryBuilder);
        return queryBuilder;
    }

    /**
     *  搜索结果封装
     * @param queryBuilder
     * @return
     */
    private Map<String, Object> searchList(NativeSearchQueryBuilder queryBuilder) {

        //指定高亮域
        HighlightBuilder.Field field = new HighlightBuilder.Field("title");

        //前缀
        field.preTags("<span style=\"color:red;\">");
        //后缀
        field.postTags("</span>");
        //碎片长度
        field.fragmentSize(100);

        //添加高亮
        queryBuilder.withHighlightFields(field);

        //创建返回结果集对象
        Map<String,Object> resultMap = new HashMap<>();
        //搜索结果集的封装
        AggregatedPage<SkuInfo> page = elasticsearchTemplate.queryForPage(
                queryBuilder.build(), //搜索条件的封装
                SkuInfo.class,  //数据集合要转换成的类型
                new SearchResultMapper() {   //执行搜索后，将数据结构封装到该对象中
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> clazz, Pageable pageable) {
                        //存储所有的转换后的数据对象
                        List<T> list = new ArrayList<T>();
                        //获取所有数据
                        SearchHits hits = searchResponse.getHits();
                        for(SearchHit hit: hits){
                            //获取非高亮数据
                            SkuInfo skuinfo = JSON.parseObject(hit.getSourceAsString(),SkuInfo.class);

                            //获取指定域的高亮数据
                            HighlightField highlightField = hit.getHighlightFields().get("title");
                            if(highlightField!=null && highlightField.getFragments() != null){
                                //高亮数据读取输出
                                Text[] fragments = highlightField.getFragments();
                                StringBuffer stringBuffer = new StringBuffer();
                                for(Text text: fragments){
                                    stringBuffer.append(text.toString());
                                }
                                //高亮数据替换非高亮数据
                                skuinfo.setTitle(stringBuffer.toString());
                            }
                            list.add((T)skuinfo);
                        }
                        /**
                         *  返回的数据：
                         *      1):搜索的集合数据：携带高亮
                         *      2):分页对象信息
                         *      3):搜索记录的总条数
                         *
                         */
                        return new AggregatedPageImpl<T>(list,pageable,searchResponse.getHits().getTotalHits());
                    }
                }
        );

        //分页记录，总记录数
        long totalElements = page.getTotalElements();
        //总页数
        int totalPages = page.getTotalPages();
        //查询的数据
        List<SkuInfo> content = page.getContent();
        //获取分页信息
        NativeSearchQuery build = queryBuilder.build();
        //分页对象
        Pageable pageable = build.getPageable();
        int pageSize = pageable.getPageSize(); //每页显示的条数
        int pageNumber = pageable.getPageNumber();//当前页

        resultMap.put("rows",content);
        resultMap.put("totalPages",totalPages);
        resultMap.put("total",totalElements);
        resultMap.put("pageSize",pageSize);
        resultMap.put("pageNumber",pageNumber);
        return resultMap;
    }

    /**
     *  聚合分组查询
     * @param nativeSearchQueryBuilder
     * @param searchMap
     * @return
     */
    public Map<String,Object> searchGroupList(NativeSearchQueryBuilder nativeSearchQueryBuilder,Map<String,String> searchMap){
        /**
         *  聚合查询：
         *      addAggregation()添加一个聚合查询
         *      terms(): 给这个桶起的名字
         *      field(): 根据哪个字段进行分桶
         *
         */
        //当用户选择了品牌，把品牌当作了搜索条件，则不需要对品牌进行分组搜索
        if(searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))){
            //品牌分组
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrand").field("brand"));
        }
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpec").field("ownSpec.keyword").size(10000));
        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);

        /**
         *  获取桶信息
         *  getAggregations() 获取的是集合，可以根据多个域进行分桶
         *  .get()获取指定桶的信息
         */
        Map<String,Object> groupMap = new HashMap<>();
        //品牌分组
        if(searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))){
            //获取分组的信息
            StringTerms BrandTerms = skuInfos.getAggregations().get("skuBrand");
            List<String> brandList = getGroupList(BrandTerms);
            groupMap.put("brandList",brandList);
        }
        //规格分组
        StringTerms specTerms = skuInfos.getAggregations().get("skuSpec");
        List<String> groupList = getGroupList(specTerms);
        //规格合并
        Map<String, Set<String>> specMap = putAllSpec(groupList);
        groupMap.put("specList",specMap);
        return groupMap;

    }

    /**
     *  获取分组后的信息
     * @param stringTerms
     * @return
     */
    private List<String> getGroupList(StringTerms stringTerms) {
        List<String> list = new ArrayList<>();
        for(StringTerms.Bucket bucket: stringTerms.getBuckets()){
            String field = bucket.getKeyAsString();
            list.add(field);
        }
        return list;
    }

    /**
     *  规格合并
     * @param specList
     * @return
     */
    private Map<String, Set<String>> putAllSpec(List<String> specList) {
        Map<String, Set<String>> allMap = new HashMap<>();
        for(String spec: specList){
           Map<String,String> specMap = JSON.parseObject(spec,Map.class);
           for(Map.Entry<String,String> entry: specMap.entrySet()){
               String key = entry.getKey();
               String value = entry.getValue();
               Set<String> specSet = allMap.get(key);
               if(!allMap.containsKey(key)){
                   specSet = new HashSet<>();
               }
               specSet.add(value);
               allMap.put(key,specSet);
           }
        }
        return allMap;
    }

    /**
     *  处理前端传来的分页参数
     * @param searchMap
     * @return
     */
    public Integer coverterPage(Map<String,String> searchMap){
        if(searchMap!=null && searchMap.size()>0){
            String pageNumber = searchMap.get("pageNumber");
            if(!StringUtils.isEmpty(pageNumber)){
                return Integer.parseInt(pageNumber);
            }else{
                return 1;
            }
        }else {
            return 1;
        }
    }
}
