package com.wbiao.controller;

import com.wbiao.annotation.Log;
import com.wbiao.pojo.Brand;
import com.wbiao.service.BrandService;
import com.wbiao.util.PageResult;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;


    @PostMapping()
    @Log
    public ResultUtil insertBrand(@RequestBody Brand brand){
        brandService.insertBrand(brand);
        return ResultUtil.ok();
    }

    /**
     *
     * @param key 搜索条件
     * @param page 当前页
     * @param rows 每页的数据量
     * @param sortBy 排序的字段
     * @param desc 排序的规则
     * @return
     */
    @GetMapping()
    @Log
    public ResultUtil selectBrand(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", required = false)Boolean desc){
        PageResult<Brand> pageResult = brandService.selectBrand(key, page, rows, sortBy, desc);
        ResultUtil resultUtil = ResultUtil.ok(pageResult);
        return resultUtil;
    }

    @GetMapping("{id}")
    @Log
    public ResultUtil selectBrandById(@PathVariable("id") Integer id){
        Brand brand = brandService.selectBrandById(id);
        ResultUtil result = ResultUtil.ok(brand);
        return result;
    }
    @PutMapping()
    @Log
    public ResultUtil updateBrand(@RequestBody Brand brand){
        brandService.updateBrandById(brand);
        return ResultUtil.ok();
    }

    @DeleteMapping("/{id}/{name}")
    @Log
    public ResultUtil deleteBrand(@PathVariable("id") Integer id,@PathVariable String name){
        brandService.deleteBrand(id,name);
        return ResultUtil.ok();
    }
}
