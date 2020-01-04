package com.wbiao.goods.feignService;

import com.wbiao.goods.pojo.Brand;
import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "wbiao-goods")
@RequestMapping("/brand")
public interface BrandFeign {
    @GetMapping("/all")
    ResultUtil<Map<String, List<Brand>>> selectAll();
}
