package com.wbiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.wbiao.annotation.Log;
import com.wbiao.service.AddressService;
import com.wbiao.user.pojo.Address;
import com.wbiao.util.ResultUtil;
import com.wbiao.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/username")
    @Log
    public ResultUtil<List<Address>> getAddressByUsername(){
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSONObject.parseObject(userinfo, Map.class);
        List<Address> addresses = addressService.selectAddressByUsername(user.get("username"));
        return ResultUtil.ok(addresses);
    }

    @PostMapping()
    @Log
    public ResultUtil add(@RequestBody Address address){
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSONObject.parseObject(userinfo, Map.class);
        address.setUsername(user.get("username"));
        addressService.addAddress(address);
        return ResultUtil.ok();
    }

    @PostMapping("/updateAddress")
    @Log
    public ResultUtil edit(@RequestBody Address address){
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSONObject.parseObject(userinfo, Map.class);
        address.setUsername(user.get("username"));
        addressService.updateAddress(address);
        return ResultUtil.ok();
    }
}
