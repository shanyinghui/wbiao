package com.wbiao.mapper;

import com.wbiao.order.pojo.Order;

public interface OrderMapper {
    void addOrder(Order order);

    void updateOrderByPay_success(Order order);

    void updateOrderByPay_failed(Order order);

    Order findOrderById(String id);
}
