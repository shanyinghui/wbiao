package com.wbiao.controller;

import com.aliyuncs.exceptions.ClientException;
import com.wbiao.annotation.Log;
import com.wbiao.service.RegisterService;
import com.wbiao.user.pojo.User;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     *  发送短信
     * @param phone
     * @return
     * @throws ClientException
     */
    @PostMapping("/code")
    @Log
    public ResultUtil sendCode(@RequestParam("phone")String phone)throws ClientException {
        //生成验证码，发送短信
        registerService.sendCode(phone);
        return ResultUtil.ok();
    }

    @PostMapping("/user")
    @Log
    public ResultUtil register(@RequestBody Map<String,Object> map){
        User user = new User();
        user.setUsername(map.get("username").toString());
        user.setPhone(map.get("phone").toString());
        user.setPassword(map.get("password").toString());
        try {
            registerService.register(user,map.get("code").toString());
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.ok();
    }

    @GetMapping("/validateUsername")
    @Log
    public ResultUtil<Boolean> validateUsername(@RequestParam("username")String username){
        User user = registerService.validateUsername(username);
        if(user==null){
            return ResultUtil.ok(true);
        }
        return ResultUtil.ok(false);
    }

    @GetMapping("/validatePhone")
    @Log
    public ResultUtil<Boolean> validatePhone(@RequestParam("phone")String phone){
        User user = registerService.phone(phone);
        if(user==null){
            return ResultUtil.ok(true);
        }
        return ResultUtil.ok(false);
    }
}
