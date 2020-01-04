package com.wbiao.controller;

import com.wbiao.annotation.Log;
import com.wbiao.service.SearchService;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PutMapping("/import")
    @Log
    public ResultUtil importData(){
        searchService.importData();
        return ResultUtil.ok();
    }

    @GetMapping("/list")
    @Log
    public ResultUtil<Map<String, Object>> searchGoods(@RequestParam(required = false)Map<String,String> searchMap){
        Map<String, Object> resultMap = searchService.SearchGoods(searchMap);
        return ResultUtil.ok(resultMap);
    }
}