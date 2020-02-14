package com.wbiao.mapper;

import com.wbiao.order.pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper {

    void addOrderItem(OrderItem orderItem);

    List<OrderItem> findOrderItemByOrder_id(String order_id);
}
