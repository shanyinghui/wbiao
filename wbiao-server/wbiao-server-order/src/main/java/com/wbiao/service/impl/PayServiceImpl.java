package com.wbiao.service.impl;

import com.wbiao.mapper.OrderMapper;
import com.wbiao.mapper.PayMapper;
import com.wbiao.order.pojo.Order;
import com.wbiao.order.pojo.Pay;
import com.wbiao.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void addPayInfo(String order_id, String transaction_id, String total_amount, String time_end) throws Exception {
        Pay pay = new Pay();
        pay.setOrder_id(order_id);
        pay.setTransaction_id(transaction_id); //交易流水号
        Order order = orderMapper.findOrderById(order_id); //查询支付的用户名称
        pay.setUsername(order.getUsername()); //用户id
        pay.setTotal_amount(Long.parseLong(total_amount)); //支付金额
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date tiem_end = sdf.parse(time_end);
        pay.setCreate_time(tiem_end); //支付完成时间
        payMapper.addPayInfo(pay);
    }
}
