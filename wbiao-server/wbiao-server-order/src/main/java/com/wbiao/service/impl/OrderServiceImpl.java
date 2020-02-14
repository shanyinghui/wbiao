package com.wbiao.service.impl;

import com.wbiao.goods.feignService.GoodsFeign;
import com.wbiao.mapper.OrderItemMapper;
import com.wbiao.mapper.OrderMapper;
import com.wbiao.order.pojo.Order;
import com.wbiao.order.pojo.OrderItem;
import com.wbiao.pay.feignService.PayFeign;
import com.wbiao.service.OrderService;
import com.wbiao.util.IdWorker;
import com.wbiao.util.ResultUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private PayFeign payFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GlobalTransactional
    @Override
    public Map<String, Object> addOrder(String tradeCode, Order order) throws Exception {
            String code = (String)redisTemplate.opsForValue().get("user:"+order.getUsername()+":tradeCode");
            //判断交易码是否一致，防止重复提交
            if(StringUtils.isNotBlank(code)&&tradeCode.equals(code)){
                //删除交易码，防止重复提交
                redisTemplate.delete("user:"+order.getUsername()+":tradeCode");

                order.setId(String.valueOf(idWorker.nextId()));
                List<String> skuids = order.getSkuids();
                if(skuids.size()==0){
                    throw new Exception("请选择下单商品！");
                }
                List<OrderItem> orderItems = new ArrayList<>();

                //将下单的商品从购物车中移除
                for(String skuid: skuids){
                    OrderItem orderItem = (OrderItem)redisTemplate.boundHashOps("cart_"+order.getUsername()).get(skuid);
                    orderItems.add(orderItem);
                    redisTemplate.boundHashOps("cart_"+order.getUsername()).delete(skuid);
                }

                BigDecimal totalMoney = new BigDecimal(0);  //订单总金额
                Map<String,Integer> decrMap = new HashMap<>();  //库存消减信息
                for(OrderItem item: orderItems){
                    item.setId(String.valueOf(idWorker.nextId()));
                    item.setOrder_id(order.getId());
                    totalMoney = totalMoney.add(item.getMoney());
                    //添加订单详情信息
                    orderItemMapper.addOrderItem(item);
                    decrMap.put(item.getSkuid(),item.getNum());
                }

                order.setTotalMoney(totalMoney); //订单总金额
                order.setPayMoney(totalMoney);   //订单应付金额
                order.setSource_type(0);    //订单来源
                order.setStatus(0);     //订单状态
                order.setPay_status(1); //支付状态
                order.setConfirm_status(0); //确认收货状态
                order.setDelete_status(0);  //删除状态

                order.setCreate_time(new Date());
                order.setModify_time(new Date());

                orderMapper.addOrder(order);
                //消减库存
                goodsFeign.decrCount(decrMap);
                //增加商品的销量
                goodsFeign.insertSale_num(decrMap);
                //设置延迟队列,监听用户是否支付
                rabbitTemplate.convertAndSend("OrderDelayQueue", (Object) order.getId(), new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        System.out.println("发送消息");
                        message.getMessageProperties().setExpiration("1800000"); //设置30分钟
                        return message;
                    }
                });
                //把订单id和订单金额返回
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("order_id",order.getId());
                resultMap.put("totalMoney",totalMoney);
                return resultMap;
            }else{
                throw new Exception("对不起，不能重复下单！");
            }
    }

    /**
     *  支付成功，修改订单
     * @param time_end
     * @param order_id
     * @throws Exception
     */
    @Override
    public void updateOrderByPay_success(String time_end,String order_id)throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date tiem_end = sdf.parse(time_end);
        Order order = new Order();
        order.setId(order_id);
        order.setStatus(1); //订单状态：1是待发货
        order.setPayment_time(tiem_end); //支付时间
        order.setPay_status(2); //支付状态，已支付
        orderMapper.updateOrderByPay_success(order);
    }

    /**
     *  支付失败，删除订单（修改）,回滚库存
     * @param order_id
     */
    @GlobalTransactional
    @Override
    public void updateOrderByPay_failed(String order_id,Integer pay_status,Date pay_date) {
        Order order = new Order();
        order.setId(order_id);
        order.setDelete_status(1);
        order.setPay_status(pay_status);

        order.setPayment_time(pay_date);

        order.setModify_time(new Date());
        orderMapper.updateOrderByPay_failed(order);
        //库存的回滚
        List<OrderItem> orderItems = orderItemMapper.findOrderItemByOrder_id(order_id);
        Map<String,Integer> parameterMap = new HashMap<>();
        for(OrderItem orderItem: orderItems){
            parameterMap.put(orderItem.getSkuid(),orderItem.getNum());
        }
        //库存的回滚
        goodsFeign.insertCount(parameterMap);
        //销量的回滚
        goodsFeign.decrSale_num(parameterMap);
    }

    /**
     * 关闭订单
     */
    @Override
    public void close(String order_id,Integer pay_status,Date pay_date) {
        ResultUtil<Map> mapResultUtil = payFeign.closePay(order_id);
        Map<String,String> closeResult = (Map) mapResultUtil.getData();
        String close_return_code1 = closeResult.get("return_code");//通信标识
        if("SUCCESS".equals(close_return_code1)){
            String close_result_code1 = closeResult.get("result_code"); //交易标识
            if("SUCCESS".equals(close_result_code1)){
                //回滚库存
                this.updateOrderByPay_failed(order_id,pay_status,pay_date);
            }
        }
    }

    @Override
    public Order findOrderById(String order_id) {
        return orderMapper.findOrderById(order_id);
    }
}
