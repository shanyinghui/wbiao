package com.wbiao.mq.listener;

import com.wbiao.order.pojo.Order;
import com.wbiao.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "orderListenerQueue")
public class OrderDelayListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void getMessage(String message){
        Order order = orderService.findOrderById(message);
        Integer pay_status = order.getPay_status();
        //如果30分钟之后，用户还未支付，就关闭订单，回滚库存
        if(pay_status.equals(1)){
            orderService.close(message,1,null);
        }
    }
}
