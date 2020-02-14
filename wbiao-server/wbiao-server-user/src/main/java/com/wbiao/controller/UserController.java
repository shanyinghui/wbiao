package com.wbiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.wbiao.annotation.Log;
import com.wbiao.service.UserService;
import com.wbiao.user.pojo.User;
import com.wbiao.util.ResultUtil;
import com.wbiao.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/{username}")
    @Log
    public ResultUtil<User> findUserByUsername(@PathVariable("username") String username){
        User user = userService.selectUserByUsername(username);
        return ResultUtil.ok(user);
    }

    /**
     *  手机登录发送短信
     */
    @PostMapping("/sendCode")
    @Log
    public ResultUtil sendCode(@RequestParam("phone")String phone)throws ClientException {
        //生成验证码，发送短信
        userService.sendCode(phone);
        return ResultUtil.ok();
    }

    /**
     *  手机登录
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/phoneLogin")
    @Log
    public ResultUtil<User> phoneLogin(@RequestParam("phone")String phone,@RequestParam("code")String code){
        User user = null;
        try {
            user = userService.phoneLogin(phone,code);
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.ok(user);
    }


    /**
     *  微博登录时，把登录信息保存到数据库
     * @param user
     */
    @PostMapping("/user")
    @Log
    public ResultUtil<User> addUserByUid(@RequestBody User user){
        try {
            User u = userService.addUser(user);
            return ResultUtil.ok(u);
        }catch (Exception e){
            e.printStackTrace();
           return ResultUtil.error(e.getMessage());
        }
    }

    /**
     *  获取当前登录用户信息
     * @return
     */
    @GetMapping("/loginUserinfo")
    @Log
    public ResultUtil<User> loginUserinfo(){
        String userinfo = TokenDecode.getUserinfo();
        Map<String,String> user = JSONObject.parseObject(userinfo,Map.class);
        String username = user.get("username");
        User u = userService.selectUserByUsername(username);
        return ResultUtil.ok(u);
    }
}
