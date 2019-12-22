package com.wbiao.controller;


import com.wbiao.annotation.Log;
import com.wbiao.pojo.Category;
import com.wbiao.service.CategoryService;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    @Log
    public ResultUtil selectCategoryByParentid(@RequestParam("pid")Integer parent_id){
        List<Category> categories = categoryService.selectCategoryByParentId(parent_id);
        return ResultUtil.ok(categories);
    }

    @PostMapping()
    @Log
    public ResultUtil insertCategory(@RequestBody Category category){
        categoryService.insertCategory(category);
        return ResultUtil.ok();
    }

    @PutMapping()
    @Log
    public ResultUtil updateCategory(String name,Integer id){
        categoryService.updateCategoryById(name,id);
        return ResultUtil.ok();
    }

    @DeleteMapping("/{id}")
    @Log
    public ResultUtil deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategoryById(id);
        return ResultUtil.ok();
    }

    @GetMapping("/id/{id}")
    @Log
    public ResultUtil selectCategoryById(@PathVariable("id") Integer id){
        Category category = categoryService.selectCategoryById(id);
        ResultUtil resultUtil = ResultUtil.ok(category);
        return resultUtil;
    }
}
