package com.wbiao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wbiao.order.feignService.OrderFeign;
import com.wbiao.order.pojo.OrderItem;
import com.wbiao.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderFeign orderFeign;

    /**
     *  当前登录用户的购物车信息
     * @param code
     * @param model
     * @return
     */
    @GetMapping()
    public String cart(@RequestParam(required = false)String code, Model model){
        if(StringUtils.isBlank(code)){
            model.addAttribute("isLogin",false);
            return "/cart";
        }
        List<OrderItem> carts = cartService.cartList(code);
        BigDecimal money = new BigDecimal(0);
        for(Object item: carts){
            OrderItem orderItem = JSON.parseObject(JSONObject.toJSONString(item, true), OrderItem.class);
            if(orderItem.getIsChecked()==1){
                money = money.add(orderItem.getMoney());
            }
        }
        model.addAttribute("allMoney",money);
        model.addAttribute("carts",carts);

        return "/cart";
    }

}

