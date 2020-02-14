package com.wbiao.controller;

import com.wbiao.annotation.Log;
import com.wbiao.goods.pojo.Goods;
import com.wbiao.goods.pojo.Sku;
import com.wbiao.goods.pojo.Spu;
import com.wbiao.service.GoodsService;
import com.wbiao.util.PageResult;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping()
    @Log
    public ResultUtil addGoods(@RequestBody Goods goods){
        goodsService.addGoods(goods);
        return ResultUtil.ok();
    }

    @GetMapping()
    @Log
    public ResultUtil<PageResult<Sku>> getGoods(
                @RequestParam("key")String key,
                @RequestParam(value = "saleable",required = false)Boolean saleable,
                @RequestParam("page")Integer page,
                @RequestParam("rows")Integer rows){
        PageResult<Sku> goodsPageResult = goodsService.selectGoods(key, saleable, page, rows);

        return ResultUtil.ok(goodsPageResult);
    }

    @GetMapping("/spu/{id}")
    @Log
    public ResultUtil<Spu> getSpuById(@PathVariable("id") String id){
        Spu spu = goodsService.selectSpuById(id);
        return ResultUtil.ok(spu);
    }

    @GetMapping("/sku")
    @Log
    public ResultUtil<List<Sku>> selectSkuBySpu_id(@RequestParam("id")String id){
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

    @GetMapping("/enable")
    @Log
    public ResultUtil<List<Sku>> selectSkuByEnable(){
        List<Sku> skus = goodsService.selectSkuByEnable();
        return ResultUtil.ok(skus);
    }

    /**
     *  商品详情查询
     * @param skuid
     * @return
     */
    @GetMapping("/goodsDetails/{skuid}")
    @Log
    public ResultUtil<Map<String, Object>> goodsDetails(@PathVariable("skuid")String skuid){
        Map<String, Object> resultMap = goodsService.goodsDetails(skuid);
        return ResultUtil.ok(resultMap);
    }


    @GetMapping("/sku/{id}")
    @Log
    public ResultUtil<Sku> selectSkuById(@PathVariable("id")String id){
        Sku sku = goodsService.selectSkuById(id);
        return ResultUtil.ok(sku);
    }

    //削减库存
    @PutMapping("/decrCount")
    @Log
    public ResultUtil decrCount(@RequestParam Map<String,Integer> decrmap){
        goodsService.decrCount(decrmap);
        return ResultUtil.ok();
    }

    //回滚库存
    @PutMapping("/insertCount")
    @Log
    public ResultUtil insertCount(@RequestParam Map<String,Integer> parameterMap){
        goodsService.insertCount(parameterMap);
        return ResultUtil.ok();
    }

    //增加商品的销量
    @PutMapping("/insertSale_num")
    @Log
    public ResultUtil insertSale_num(@RequestParam Map<String,Integer> decrmap){
        goodsService.insertSale_num(decrmap);
        return ResultUtil.ok();
    }

    //销量的回滚
    @PutMapping("/decrSale_num")
    @Log
    public ResultUtil decrSale_num(@RequestParam Map<String,Integer> parameterMap){
        goodsService.decrSale_num(parameterMap);
        return ResultUtil.ok();
    }
}
