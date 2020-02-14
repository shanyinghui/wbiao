package com.wbiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.wbiao.annotation.Log;
import com.wbiao.order.pojo.Order;
import com.wbiao.service.OrderService;
import com.wbiao.util.ResultUtil;
import com.wbiao.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @Log
    public ResultUtil<Map<String,Object>> add(@RequestBody Order order, @RequestParam("tradeCode")String tradeCode)throws Exception{
        if(tradeCode==null || "".equals(tradeCode)){
            return ResultUtil.error("订单提交失败");
        }
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSONObject.parseObject(userinfo, Map.class);
        order.setUsername(user.get("username"));
        Map<String, Object> map = orderService.addOrder(tradeCode, order);
        return ResultUtil.ok(map);
    }

}
