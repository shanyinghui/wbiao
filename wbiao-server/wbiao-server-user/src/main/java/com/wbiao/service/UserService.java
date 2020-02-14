package com.wbiao.service;

import com.aliyuncs.exceptions.ClientException;
import com.wbiao.user.pojo.User;

public interface UserService {

    User selectUserByUsername(String username);

    void sendCode(String phone)throws ClientException;

    User phoneLogin(String phone, String code) throws Exception;

    User addUser(User user);
}
