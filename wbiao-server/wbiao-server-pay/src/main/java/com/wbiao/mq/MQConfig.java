package com.wbiao.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig{
    @Value("${mq.pay.queue.order}")
    private String queue;
    @Value("${mq.pay.exchange.order}")
    private String exchange;
    @Value("${mq.pay.routing.key}")
    private String routingKey;

    //创建队列
    @Bean
    public Queue orderQueue(){
        return new Queue(queue);
    }
    //创建交换机
    @Bean
    public Exchange orderExchange(){
        //第二个参数：是否持久化，第三个参数：是否自动删除
        return new DirectExchange(exchange,true,false);
    }

    //队列绑定交换机
    @Bean
    public Binding orderQueueExchange(Queue orderQueue,Exchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(routingKey).noargs();
    }
}
