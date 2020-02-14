package com.wbiao.order.feignService;

import com.wbiao.order.pojo.OrderItem;
import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "wbiao-order")
@RequestMapping("/cart")
public interface CartFeign {

    /**
     *  查询选中的购物车列表
     */
    @GetMapping("/cartByChecked")
    ResultUtil<List<OrderItem>> cartByChecked();
}
