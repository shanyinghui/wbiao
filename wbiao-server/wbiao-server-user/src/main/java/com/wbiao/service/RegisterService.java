package com.wbiao.service;

import com.aliyuncs.exceptions.ClientException;
import com.wbiao.user.pojo.User;

public interface RegisterService {

    //生成验证码，发送短信，把验证码存入redis
    void sendCode(String phone) throws ClientException;

    //注册用户
    void register(User user,String code) throws Exception;

    //验证用户名是否存在
    User validateUsername(String username);

    //验证手机号是否存在
    User phone(String phone);
}
