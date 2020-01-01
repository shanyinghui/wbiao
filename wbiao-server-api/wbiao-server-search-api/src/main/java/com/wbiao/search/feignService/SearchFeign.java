package com.wbiao.search.feignService;

import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "wbiao-search")
@RequestMapping("/search")
public interface SearchFeign {
    @GetMapping("/list")
    ResultUtil searchGoods(@RequestParam(required = false)Map<String,String> searchMap);
}
