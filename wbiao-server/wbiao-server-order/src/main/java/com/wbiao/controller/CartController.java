package com.wbiao.controller;

import com.alibaba.fastjson.JSON;
import com.wbiao.annotation.Log;
import com.wbiao.order.pojo.OrderItem;
import com.wbiao.service.CartService;
import com.wbiao.util.ResultUtil;
import com.wbiao.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     *  加入购物车
     */
    @PostMapping("/addCarts")
    @Log
    public ResultUtil addCart(@RequestBody List<OrderItem> orderItems){
        Map<String,Object> itemMap = new HashMap<>();
        for(OrderItem item: orderItems){
            itemMap.put(item.getSkuid(),item.getNum());
        }
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSON.parseObject(userinfo, Map.class);
        for(Map.Entry<String,Object> entry: itemMap.entrySet()){
            String skuid = entry.getKey();
            Integer num = (Integer)entry.getValue();
            cartService.addCart(skuid,num,user.get("username"));
        }

        return ResultUtil.ok();
    }

    /**
     *  购物车列表
     */
    @GetMapping("/list")
    @Log
    public ResultUtil<List<OrderItem>> list(){
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSON.parseObject(userinfo, Map.class);
        List<OrderItem> orderItems = cartService.findCartByUsername(user.get("username"));
        return ResultUtil.ok(orderItems);
    }

    /**
     *  删除购物车中的数据
     * @return
     */
    @RequestMapping("/delete")
    @Log
    public ResultUtil deleteMore(@RequestParam(value="skuids")String[] skuids){
        List<String> ids = null;
        if(skuids!=null){
            ids = Arrays.asList(skuids);
        }
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSON.parseObject(userinfo, Map.class);
        cartService.deleteCart(user.get("username"),ids);
        return ResultUtil.ok();
    }

    /**
     *  修改购物车数量
     * @param skuid
     * @param num
     * @return
     */
    @PutMapping("/{skuid}/{num}")
    @Log
    public ResultUtil updateCartNum(@PathVariable("skuid") String skuid,@PathVariable("num") Integer num){
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSON.parseObject(userinfo, Map.class);
        cartService.updateCartNum(user.get("username"),skuid,num);
        return ResultUtil.ok();
    }

    /**
     *  修改购物车选中状态
     * @param skuid
     * @param isChecked
     * @return
     */
    @PutMapping("/updateIsChecked/{skuid}/{isChecked}")
    @Log
    public ResultUtil updateIsChecked(@PathVariable("skuid") String skuid,@PathVariable("isChecked") Integer isChecked){
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSON.parseObject(userinfo, Map.class);
        cartService.updateCartIsChecked(user.get("username"),skuid,isChecked);
        return ResultUtil.ok();
    }

    /**
     *  查询选中的购物车列表
     */
    @GetMapping("/cartByChecked")
    @Log
    public ResultUtil<List<OrderItem>> cartByChecked(){
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSON.parseObject(userinfo, Map.class);
        List<OrderItem> orderItems = cartService.findCartByUsername(user.get("username"));
        List<OrderItem> items = new ArrayList<>();
        for(OrderItem item: orderItems){
            if(item.getIsChecked().equals(1)){
                items.add(item);
            }
        }
        return ResultUtil.ok(items);
    }

}
