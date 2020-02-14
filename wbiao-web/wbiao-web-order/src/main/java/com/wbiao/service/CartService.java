package com.wbiao.service;

import com.wbiao.order.pojo.OrderItem;

import java.util.List;

public interface CartService {
    List<OrderItem> cartList(String token);
}
