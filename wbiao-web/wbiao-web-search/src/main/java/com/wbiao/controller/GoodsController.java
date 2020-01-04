package com.wbiao.controller;

import com.wbiao.annotation.Log;
import com.wbiao.goods.feignService.BrandFeign;
import com.wbiao.goods.pojo.Brand;
import com.wbiao.search.feignService.SearchFeign;
import com.wbiao.search.pojo.SkuInfo;
import com.wbiao.util.Page;
import com.wbiao.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {

    @Autowired
    private SearchFeign searchFeign;

    @Autowired
    private BrandFeign brandFeign;

    @GetMapping("/search.html")
    public String search(@RequestParam(required = false) Map<String, String> searchMap, Model model) {
        if (searchMap != null && searchMap.size() > 0) {
            if (!StringUtils.isEmpty(searchMap.get("startPrice")) || !StringUtils.isEmpty(searchMap.get("endPrice"))) {
                String startPrice = StringUtils.isEmpty(searchMap.get("startPrice")) ? "0" : searchMap.get("startPrice");
                String endPrice = StringUtils.isEmpty(searchMap.get("endPrice")) ? "0" : searchMap.get("endPrice");
                String price = "";
                if (endPrice.equals("0")) {
                    price = startPrice + "以上";
                } else {
                    price = startPrice + "-" + endPrice;
                }
                searchMap.put("price", price);
            }
        }
        searchMap.remove("startPrice");
        searchMap.remove("endPrice");

        ResultUtil resultUtil = searchFeign.searchGoods(searchMap);

        Map<String, Object> data = (Map) resultUtil.getData();

        //分页对象
        Page<SkuInfo> page = new Page<SkuInfo>(
                Long.parseLong(data.get("total").toString()),
                Integer.parseInt(data.get("pageNumber").toString()) + 1,
                Integer.parseInt(data.get("pageSize").toString()));
        model.addAttribute("page", page);

        model.addAttribute("result", data);

        //将条件存储用于页面回显
        model.addAttribute("searchMap", searchMap);

        String[] urls = url(searchMap);
        model.addAttribute("url", urls[0]);
        model.addAttribute("sorturl", urls[1]);
        return "search";
    }

    /**
     * 获取用户每次请求的地址，
     * 页面需要在此次地址的基础上追加搜索条件
     */
    public String[] url(Map<String, String> searchMap) {
        String url = "/search.html";
        String sorturl = "/search.html";
        if (searchMap != null && searchMap.size() > 0) {
            url += "?";
            sorturl += "?";
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                //key是搜索的条件名称
                String key = entry.getKey();
                //value是搜索的值
                String value = entry.getValue();

                if (key.equalsIgnoreCase("pageNumber")) {
                    continue;
                }
                url += key + "=" + value + "&";

                if (key.equalsIgnoreCase("sortField") || key.equalsIgnoreCase("sortRule")) {
                    continue;
                }

                sorturl += key + "=" + value + "&";

            }

            //去掉最后一个&
            url = url.substring(0, url.length() - 1);
            sorturl = sorturl.substring(0, sorturl.length() - 1);
        }
        return new String[]{url, sorturl};
    }


    @GetMapping("/index.html")
    @Log
    public String index(Model model) {
        //index页面的品牌展示
        ResultUtil resultUtil = brandFeign.selectAll();
        Map<String, List<Brand>> data = (Map) resultUtil.getData();
        model.addAttribute("brands", data);

        //index页面的猜你喜欢数据
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("sortField", "sale_num");
        searchMap.put("sortRule", "DESC");
        ResultUtil resultUtil1 = searchFeign.searchGoods(searchMap);
        model.addAttribute("likeData", resultUtil1.getData());
        return "index";
    }
}
