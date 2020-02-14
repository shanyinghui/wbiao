package com.wbiao.goods.feignService;

import com.wbiao.goods.pojo.Sku;
import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "wbiao-goods")
@RequestMapping("/goods")
public interface GoodsFeign {
    @GetMapping("/enable")
    ResultUtil<List<Sku>> selectSkuByEnable();

    @GetMapping("/goodsDetails/{skuid}")
    ResultUtil<Map<String, Object>> goodsDetails(@PathVariable("skuid")String skuid);

    @GetMapping("/sku/{id}")
    ResultUtil<Sku> selectSkuById(@PathVariable("id")String id);

    @PutMapping("/decrCount")
    ResultUtil decrCount(@RequestParam Map<String,Integer> decrmap);

    //回滚库存
    @PutMapping("/insertCount")
    ResultUtil insertCount(@RequestParam Map<String,Integer> parameterMap);

    //增加商品的销量
    @PutMapping("/insertSale_num")
    ResultUtil insertSale_num(@RequestParam Map<String,Integer> decrmap);

    //销量的回滚
    @PutMapping("/decrSale_num")
    ResultUtil decrSale_num(@RequestParam Map<String,Integer> parameterMap);
}
