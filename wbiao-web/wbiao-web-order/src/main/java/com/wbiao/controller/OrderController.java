package com.wbiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.wbiao.order.feignService.CartFeign;
import com.wbiao.order.pojo.OrderItem;
import com.wbiao.user.feignService.AddressFeign;
import com.wbiao.user.pojo.Address;
import com.wbiao.util.ResultUtil;
import com.wbiao.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private AddressFeign addressFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  跳转到商品结算页
     * @return
     */
    @RequestMapping("/shopping-order.html")
    public String shopping_order(){

        return "/shopping-order";
    }

    @RequestMapping("/getCartInfo")
    @ResponseBody
    public ResultUtil<Map<String,Object>> getCartInfo(@RequestParam(value = "flag",required = false)String flag){
        Map<String,Object> resultMap = new HashMap<>();
        if(flag!=null){
            //结算商品信息
            ResultUtil<List<OrderItem>> resultUtil = cartFeign.cartByChecked();
            resultMap.put("carts",resultUtil.getData());
        }
        //收货地址信息
        ResultUtil<List<Address>> addressByUsername = addressFeign.getAddressByUsername();
        resultMap.put("address",addressByUsername.getData());
        //生成交易码
        String tradeCode = UUID.randomUUID().toString();
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSONObject.parseObject(userinfo, Map.class);
        redisTemplate.opsForValue().set("user:"+user.get("username")+":tradeCode",tradeCode);

        resultMap.put("tradeCode",tradeCode);
        return ResultUtil.ok(resultMap);
    }

    /**
     *  跳转到支付页面
     *
     * @param id
     * @param totalMoney
     * @param model
     * @return
     */
    @RequestMapping("/pay.html")
    public String pay(@RequestParam("id")String id, @RequestParam("totalMoney")Integer totalMoney, Model model){
        model.addAttribute("id",id);
        model.addAttribute("totalMoney",totalMoney);
        return "/pay";
    }

    @RequestMapping("/success.html")
    public String success(){
        return "/paysuccess";
    }

    @RequestMapping("/fail.html")
    public String fail(){
        return "/payfail";
    }

}
