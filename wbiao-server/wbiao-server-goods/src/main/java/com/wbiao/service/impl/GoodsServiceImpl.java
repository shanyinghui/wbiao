package com.wbiao.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wbiao.goods.pojo.*;
import com.wbiao.mapper.SkuMapper;
import com.wbiao.mapper.SpecMapper;
import com.wbiao.mapper.SpuMapper;
import com.wbiao.service.GoodsService;
import com.wbiao.service.SpecService;
import com.wbiao.util.IdWorker;
import com.wbiao.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecMapper specMapper;

    @Autowired
    private SpecService specService;

    //分布式id生成解决方案，基于开源算法snowflake
    @Autowired
    private IdWorker idWorker;

    @Transactional
    @Override
    public void addGoods(Goods goods) {
        Spu spu = goods.getSpu();
        Long spu_id = (Long)idWorker.nextId();
        spu.setId(spu_id.toString());
        spu.setCreatetime(new Date());
        spu.setLast_updatetime(new Date());
        //先添加spu
        spuMapper.addSpu(spu);
        long l = idWorker.nextId();
        List<Sku> skus = goods.getSkus();
        //循环添加sku
        if(skus!=null && skus.size()>0){
            for (Sku sku: skus){
                Long sku_id = (Long)idWorker.nextId();
                sku.setId(sku_id.toString());
                sku.setSpu_id(spu_id.toString());
                sku.setBrand(spu.getBrand());
                if(StringUtils.isNotBlank(sku.getImages())){
                    String[] images = sku.getImages().split(",");
                    sku.setImage(images[0]);
                }
                sku.setComment_num(0L);
                sku.setSale_num(0L);
                sku.setCreatetime(new Date());
                sku.setLast_updatetime(new Date());
                skuMapper.addSku(sku);

                //添加销售属性值
                SkuSaleVal skuSaleVal = new SkuSaleVal();
                skuSaleVal.setSpuid(spu_id.toString());
                skuSaleVal.setSkuid(sku_id.toString());
                skuSaleVal.setImage(sku.getImage());
                String ownSpec = sku.getOwnSpec();
                if(!StringUtils.isEmpty(ownSpec)){
                    String val = getSkuSaleVal(ownSpec);
                    skuSaleVal.setSalaVal(val);
                    specMapper.addSkuSaleVal(skuSaleVal);
                }
            }
        }
    }

    @Override
    public PageResult<Sku> selectGoods(String key, Boolean saleable, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<Sku> skus = skuMapper.selectSku(key, saleable);
        PageInfo<Sku> pageInfo = new PageInfo<>(skus);
        PageResult<Sku> pr = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return pr;
    }

    @Override
    public Spu selectSpuById(String id) {
        return spuMapper.selectSpuById(id);
    }

    @Override
    public List<Sku> selectSkuBySpu_id(String id) {
        return skuMapper.selectSkuBySpu_id(id);
    }

    @Transactional
    @Override
    public void updateGoods(Goods goods) {
        Spu spu = goods.getSpu();
        List<Sku> skus = goods.getSkus();

        spu.setLast_updatetime(new Date());
        //修改spu表信息
        spuMapper.updateSpuById(spu);
        //删除所有的sku
        skuMapper.deleteSkuBySpu_id(spu.getId());
        //删除完循环添加
        if (skus != null && skus.size() > 0) {
            for (Sku sku : skus) {
                //判断sku的id是否存在，如存在则此sku不是新添加的
                if (StringUtils.isNotBlank(sku.getId())) {
                    sku.setSpu_id(spu.getId());
                    sku.setBrand(spu.getBrand());
                    sku.setLast_updatetime(new Date());
                    if (StringUtils.isNotBlank(sku.getImages())) {
                        String[] images = sku.getImages().split(",");
                        sku.setImage(images[0]);
                    }
                    skuMapper.addSku(sku);

                    //添加销售属性值
                    SkuSaleVal skuSaleVal = new SkuSaleVal();
                    skuSaleVal.setSpuid(spu.getId());
                    skuSaleVal.setSkuid(sku.getId());
                    skuSaleVal.setImage(sku.getImage());
                    String ownSpec = sku.getOwnSpec();
                    if(!StringUtils.isEmpty(ownSpec)){
                        String val = getSkuSaleVal(ownSpec);
                        skuSaleVal.setSalaVal(val);
                        specMapper.updateSkuSaleVal(skuSaleVal);
                    }
                } else { //此sku是新添加的
                    Long sku_id = (Long) idWorker.nextId();
                    sku.setId(sku_id.toString());
                    sku.setSpu_id(spu.getId());
                    sku.setBrand(spu.getBrand());
                    if (StringUtils.isNotBlank(sku.getImages())) {
                        String[] images = sku.getImages().split(",");
                        sku.setImage(images[0]);
                    }
                    sku.setComment_num(0L);
                    sku.setSale_num(0L);
                    sku.setCreatetime(new Date());
                    sku.setLast_updatetime(new Date());
                    skuMapper.addSku(sku);

                    //添加销售属性值
                    SkuSaleVal skuSaleVal = new SkuSaleVal();
                    skuSaleVal.setSpuid(spu.getId());
                    skuSaleVal.setSkuid(sku.getId());
                    skuSaleVal.setImage(sku.getImage());
                    String ownSpec = sku.getOwnSpec();
                    if(!StringUtils.isEmpty(ownSpec)){
                        String val = getSkuSaleVal(ownSpec);
                        skuSaleVal.setSalaVal(val);
                        specMapper.addSkuSaleVal(skuSaleVal);
                    }
                }
            }
        }
    }

    @Override
    public void deleteSkuById(String id) {
        skuMapper.deleteSkuById(id);
    }

    @Override
    public void updateSkuEnable(String id) {
        skuMapper.updateSkuEnable(id);
    }

    @Override
    public List<Sku> selectSkuByEnable() {
        return skuMapper.selectSkuByEnable();
    }


    @Override
    public Map<String, Object> goodsDetails(String skuid) {
        //查询sku商品信息
        Sku sku= skuMapper.selectSkuById(skuid);

        //查询sku商品所属系列
        Spu spu = spuMapper.selectSpuById(sku.getSpu_id());
        String series = spu.getSeries();

        //根据spuid查询所有销售属性
        List<SkuSaleVal> skuSaleVals = specMapper.selectAllBySpuid(sku.getSpu_id());

        //查询规格参数
        List<SpecGroup> groups = specService.selectAllSpecGroup();

        //处理sku的规格参数
        Map<String,String> specMap = JSON.parseObject(sku.getOwnSpec(),Map.class);

        Map<String,Map<String,String>> map = new HashMap<>();
        for(SpecGroup group: groups){
            List<Spec> specs = group.getSpecs();
            for(Spec spec: specs){
                for(Map.Entry<String,String> entry: specMap.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if(key.equalsIgnoreCase(spec.getName())){
                        Map<String,String> spec_valMap = new HashMap<>();
                        if(map.containsKey(group.getName())){
                            spec_valMap = map.get(group.getName());
                            spec_valMap.put(key,value);
                        }else{
                            spec_valMap.put(key,value);
                            map.put(group.getName(),spec_valMap);
                        }
                    }
                }
            }
        }
        //处理sku的图片
        String images = sku.getImages();
        List<String> imgs = Arrays.asList(images.split(","));

        SkuDetails skuDetails = JSON.parseObject(JSON.toJSONString(sku), SkuDetails.class);
        skuDetails.setImgs(imgs);
        skuDetails.setSpecMap(map);
        skuDetails.setSeries(series);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("sku",skuDetails);
        resultMap.put("skuSaleVals",skuSaleVals);
        return resultMap;
    }

    @Override
    public Sku selectSkuById(String id) {
        return skuMapper.selectSkuById(id);
    }

    /**
     *  下单的同时消减库存
     * @param decrmap
     */
    @Override
    public void decrCount(Map<String, Integer> decrmap){
        for(Map.Entry<String,Integer> entry: decrmap.entrySet()){
            String id = entry.getKey();
            Object val = entry.getValue();
            Integer num = Integer.valueOf(val.toString());

            int i = skuMapper.decrCount(id, num);
            if(i<=0){
                throw new RuntimeException("商品库存不足！");
            }
        }
    }

    /**
     *  回滚库存
     * @param parameterMap
     */
    @Override
    public void insertCount(Map<String, Integer> parameterMap) {
        for(Map.Entry<String,Integer> entry: parameterMap.entrySet()){
            String id = entry.getKey();
            Object val = entry.getValue();
            Integer num = Integer.valueOf(val.toString());
            skuMapper.InsertCount(id, num);
        }
    }

    /**
     *  增加销量
     * @param parameterMap
     */
    @Override
    public void insertSale_num(Map<String, Integer> parameterMap) {
        for(Map.Entry<String,Integer> entry: parameterMap.entrySet()){
            String id = entry.getKey();
            Object val = entry.getValue();
            Integer sale_num = Integer.valueOf(val.toString());
            skuMapper.insertSale_num(id, sale_num);
        }
    }

    /**
     *  订单支付失败，回滚销量
     * @param parameterMap
     */
    @Override
    public void decrSale_num(Map<String, Integer> parameterMap) {
        for(Map.Entry<String,Integer> entry: parameterMap.entrySet()){
            String id = entry.getKey();
            Object val = entry.getValue();
            Integer sale_num = Integer.valueOf(val.toString());
            skuMapper.decrSale_num(id, sale_num);
        }
    }

    private String getSkuSaleVal(String ownSpec) {
        String specVal = "";
        Map<String,String> specMap = JSON.parseObject(ownSpec,Map.class);
        for(Map.Entry<String,String> entry: specMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.equalsIgnoreCase("外壳材质")){
                specVal += " "+value;
            }
            if(key.equalsIgnoreCase("表盘颜色")){
                specVal += " "+value;
            }
            if(key.equalsIgnoreCase("表带材质")){
                specVal += " "+value;
            }
        }
        return specVal;
    }
}
