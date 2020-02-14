package com.wbiao.service;

import com.wbiao.order.pojo.OrderItem;

import java.util.List;

public interface CartService {
    void addCart(String id,Integer num,String username);

    List<OrderItem> findCartByUsername(String username);

    void deleteCart(String username, List<String> ids);

    void updateCartNum(String username, String skuid, Integer num);

    void updateCartIsChecked(String username,String skuid,Integer isChecked);
}
