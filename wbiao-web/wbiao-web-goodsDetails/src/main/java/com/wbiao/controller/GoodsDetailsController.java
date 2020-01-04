package com.wbiao.controller;

import com.wbiao.service.GoodsDetailsPageService;
import com.wbiao.service.GoodsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class GoodsDetailsController {
    @Autowired
    private GoodsDetailsService goodsDetailsService;

    @Autowired
    private GoodsDetailsPageService goodsDetailsPageService;

    @GetMapping("/goodsDetails/{skuid}.html")
    public String goodsDetails(@PathVariable("skuid") String skuid, Model model){
        Map<String, Object> resultMap = goodsDetailsService.goodsDetails(skuid);
        model.addAttribute("goodsDetails",resultMap);

        /*//页面静态化
        goodsDetailsPageService.CreatePage(skuid);*/
        return "goodsDetails";
    }
}
