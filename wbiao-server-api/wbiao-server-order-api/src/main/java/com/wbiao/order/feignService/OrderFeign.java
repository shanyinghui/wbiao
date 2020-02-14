package com.wbiao.order.feignService;

import com.wbiao.order.pojo.OrderItem;
import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "wbiao-order")
public interface OrderFeign {

    /**
     *
     *
     *  加入购物车
     */
    @GetMapping("/{id}/{num}")
    ResultUtil addCart(@PathVariable("id")String id, @PathVariable("num")Integer num);

    /**
     *  购物车列表
     */
    @GetMapping("/list")
    ResultUtil<List<OrderItem>> list();

}
