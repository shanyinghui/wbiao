package com.wbiao.controller;

import com.wbiao.annotation.Log;
import com.wbiao.pojo.Goods;
import com.wbiao.pojo.Sku;
import com.wbiao.pojo.Spu;
import com.wbiao.service.GoodsService;
import com.wbiao.util.PageResult;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping()
    @Log
    public ResultUtil addGoods(@RequestBody Goods goods){
        System.out.println(goods);
        goodsService.addGoods(goods);
        return ResultUtil.ok();
    }

    @GetMapping()
    @Log
    public ResultUtil getGoods(
                @RequestParam("key")String key,
                @RequestParam(value = "saleable",required = false)Boolean saleable,
                @RequestParam("page")Integer page,
                @RequestParam("rows")Integer rows){
        PageResult<Sku> goodsPageResult = goodsService.selectGoods(key, saleable, page, rows);

        return ResultUtil.ok(goodsPageResult);
    }

    @GetMapping("/spu/{id}")
    @Log
    public ResultUtil getSpuById(@PathVariable("id") String id){
        Spu spu = goodsService.selectSpuById(id);
        return ResultUtil.ok(spu);
    }

    @GetMapping("/sku")
    @Log
    public ResultUtil selectSkuBySpu_id(@RequestParam("id")String id){
        List<Sku> skus = goodsService.selectSkuBySpu_id(id);
        return ResultUtil.ok(skus);
    }

    @PutMapping()
    @Log
    public ResultUtil updateGoods(@RequestBody Goods goods){
        goodsService.updateGoods(goods);
        return ResultUtil.ok();
    }

    @DeleteMapping("/sku/{id}")
    @Log
    public ResultUtil deleteSku(@PathVariable("id") String id){
        goodsService.deleteSkuById(id);
        return ResultUtil.ok();
    }

    @PutMapping("/sku_enable/{id}")
    @Log
    public ResultUtil updateEnable(@PathVariable("id") String id){
        goodsService.updateSkuEnable(id);
        return ResultUtil.ok();
    }
}
