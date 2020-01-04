package com.wbiao.controller;

import com.wbiao.annotation.Log;
import com.wbiao.goods.pojo.Spec;
import com.wbiao.service.SpecService;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    @GetMapping()
    @Log
    public ResultUtil<List<Spec>> selectSpec(){
        List<Spec> specs = specService.selectSpec();
        return ResultUtil.ok(specs);
    }
}
