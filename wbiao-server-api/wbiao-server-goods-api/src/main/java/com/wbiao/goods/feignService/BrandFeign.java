package com.wbiao.goods.feignService;

import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "wbiao-goods")
@RequestMapping("/brand")
public interface BrandFeign {
    @GetMapping("/all")
    ResultUtil selectAll();
}
