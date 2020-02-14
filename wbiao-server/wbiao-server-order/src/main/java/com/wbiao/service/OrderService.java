package com.wbiao.service;

import com.wbiao.order.pojo.Order;

import java.util.Date;
import java.util.Map;

public interface OrderService {
    Map<String, Object> addOrder(String tradeCode, Order order) throws Exception;

    void updateOrderByPay_success(String time_end,String order_id) throws Exception;

    void updateOrderByPay_failed(String order_id,Integer pay_status, Date pay_date);

    void close(String order_id,Integer pay_status,Date pay_date);

    Order findOrderById(String order_id);
}
