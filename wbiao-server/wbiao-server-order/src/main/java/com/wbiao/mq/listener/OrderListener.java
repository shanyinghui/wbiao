package com.wbiao.mq.listener;

import com.alibaba.fastjson.JSONObject;
import com.wbiao.service.OrderService;
import com.wbiao.service.PayService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@RabbitListener(queues = "${mq.pay.queue.order}")
public class OrderListener {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;


    /**
     *  支付结果监听
     */
    @RabbitHandler
    public void getMessage(String message){
        try {
            System.out.println("监听到的消息："+message);

            //支付结果
            Map<String,String> resultmap = JSONObject.parseObject(message,Map.class);
            //return_code 通信标识
            String return_code = resultmap.get("return_code");
            if("SUCCESS".equals(return_code)){
                //商户订单号 out_trade_no
                String out_trade_no = resultmap.get("out_trade_no");

                //result_code 返回结果
                String result_code = resultmap.get("result_code");
                //支付成功
                if("SUCCESS".equals(result_code)) {
                    //交易流水号 transaction_id
                    String transaction_id = resultmap.get("transaction_id");
                    //添加支付信息
                    payService.addPayInfo(out_trade_no,transaction_id,resultmap.get("total_fee"),resultmap.get("time_end"));
                    //修改订单信息
                    orderService.updateOrderByPay_success(resultmap.get("time_end"),out_trade_no);
                }else {
                    //支付失败，关闭订单，回滚库存
                    orderService.close(out_trade_no,3,new Date());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
