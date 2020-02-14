package com.wbiao.service.impl;

import com.wbiao.goods.feignService.GoodsFeign;
import com.wbiao.goods.pojo.Sku;
import com.wbiao.order.pojo.OrderItem;
import com.wbiao.service.CartService;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  添加购物车
     * @param id
     * @param num
     * @param username
     */
    @Override
    public void addCart(String id, Integer num,String username) {
        //当购物车数量<=0,移除该商品
        if(num<=0){
            redisTemplate.boundHashOps("cart_"+username).delete(id);

            Long size = redisTemplate.boundHashOps("cart_" + username).size();
            //如果购物车为空，连同购物车一起删除
            if(size == null || size <= 0){
                redisTemplate.delete("cart_"+username);
            }
            return;
        }

        //查看购物车中是否存在该商品
        Boolean hasKey = redisTemplate.boundHashOps("cart_" + username).hasKey(id);
        if(hasKey){ //如果存在该商品，进行累加
            OrderItem orderItem  = (OrderItem)redisTemplate.boundHashOps("cart_" + username).get(id);
            Integer n = orderItem.getNum();
            orderItem.setNum(num+n);
            orderItem.setMoney(orderItem.getPrice().multiply(new BigDecimal(orderItem.getNum())));
            //将购物车信息存入到redis中
            redisTemplate.boundHashOps("cart_" + username).put(id,orderItem);
            return ;
        }

        ResultUtil<Sku> resultUtil = goodsFeign.selectSkuById(id);
        Sku sku = (Sku)resultUtil.getData();
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuid(sku.getId());
        orderItem.setSpuid(sku.getSpu_id());
        orderItem.setImage(sku.getImage());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setTitle(sku.getTitle());
        orderItem.setMoney(sku.getPrice().multiply(new BigDecimal(num)));
        orderItem.setIsChecked(0);

        //将购物车信息存入到redis中
        redisTemplate.boundHashOps("cart_"+username).put(id,orderItem);

    }

    /**
     *  查询购物车
     * @param username
     * @return
     */
    @Override
    public List<OrderItem> findCartByUsername(String username) {
        return redisTemplate.boundHashOps("cart_"+username).values();
    }

    /**
     *  删除购物车中的数据
     * @param username
     * @param ids
     */
    @Override
    public void deleteCart(String username, List<String> ids) {
        if(ids!=null&&ids.size()>0) {
            for (String id : ids) {
                redisTemplate.boundHashOps("cart_" + username).delete(id);
            }
        }
    }

    /**
     *  修改购物车的数量
     * @param username
     * @param skuid
     * @param num
     */
    @Override
    public void updateCartNum(String username, String skuid, Integer num) {
        //当购物车数量<=0,移除该商品
        if(num<=0){
            redisTemplate.boundHashOps("cart_"+username).delete(skuid);

            Long size = redisTemplate.boundHashOps("cart_" + username).size();
            //如果购物车为空，连同购物车一起删除
            if(size == null || size <= 0){
                redisTemplate.delete("cart_"+username);
            }
            return;
        }
        OrderItem orderItem  = (OrderItem)redisTemplate.boundHashOps("cart_" + username).get(skuid);
        orderItem.setNum(num);
        orderItem.setMoney(orderItem.getPrice().multiply(new BigDecimal(num)));
        //将购物车信息存入到redis中
        redisTemplate.boundHashOps("cart_" + username).put(skuid,orderItem);
    }

    /**
     *  修改购物车选中状态
     * @param skuid
     * @param isChecked
     * @return
     */
    @Override
    public void updateCartIsChecked(String username, String skuid, Integer isChecked) {
        OrderItem orderItem  = (OrderItem)redisTemplate.boundHashOps("cart_" + username).get(skuid);
        orderItem.setIsChecked(isChecked);
        //将购物车信息存入到redis中
        redisTemplate.boundHashOps("cart_" + username).put(skuid,orderItem);
    }


}
