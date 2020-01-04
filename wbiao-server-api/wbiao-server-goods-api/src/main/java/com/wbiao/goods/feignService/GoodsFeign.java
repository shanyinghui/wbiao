package com.wbiao.goods.feignService;

import com.wbiao.goods.pojo.Sku;
import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "wbiao-goods")
@RequestMapping("/goods")
public interface GoodsFeign {
    @GetMapping("/enable")
    ResultUtil<List<Sku>> selectSkuByEnable();

    @GetMapping("/goodsDetails/{skuid}")
    ResultUtil<Map<String, Object>> goodsDetails(@PathVariable("skuid")String skuid);
}
