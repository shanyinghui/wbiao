package com.wbiao.mq.queue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  设置消息延迟队列，监听订单生成30分钟后，用户是否支付
 */
@Configuration
public class QueueConfig {

    /**
     *  创建queue1
     * @return
     */
    @Bean
    public Queue OrderDelayQueue(){
        return QueueBuilder
                .durable("OrderDelayQueue")
                //消息超时进入死信队列，绑定死信队列交换机
                .withArgument("x-dead-letter-exchange","orderListenerExchange")
                //超时数据发送给哪个队列
                .withArgument("x-dead-letter-routing-key","orderListenerQueue")
                .build();
    }

    /**
     *  创建queue2
     * @return
     */
    @Bean
    public Queue orderListenerQueue(){
        return new Queue("orderListenerQueue",true);
    }

    /**
     *  创建交换机
     */
    @Bean
    public Exchange orderListenerExchange(){
        return new DirectExchange("orderListenerExchange");
    }

    /**
     *  把queue2绑定到交换机
     */
    @Bean
    public Binding binding(Queue orderListenerQueue, Exchange orderListenerExchange){
        return BindingBuilder.bind(orderListenerQueue).to(orderListenerExchange).with("orderListenerQueue").noargs();
    }
}
