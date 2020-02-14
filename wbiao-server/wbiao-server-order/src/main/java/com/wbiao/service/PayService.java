package com.wbiao.service;

public interface PayService {
    void addPayInfo(String order_id,String transaction_id,String total_amount,String time_end) throws Exception;
}
