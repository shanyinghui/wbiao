package com.wbiao.service;

import java.util.Map;

public interface WeixinPayService {

    //创建订单
    Map createNative(Map<String,String> paramter);

    //支付查询
    Map queryState(String outtradeno);

    //关闭订单
    Map close(String order_id);
}
