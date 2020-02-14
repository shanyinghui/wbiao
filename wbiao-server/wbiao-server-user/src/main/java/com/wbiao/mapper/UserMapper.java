package com.wbiao.mapper;

import com.wbiao.user.pojo.User;

public interface UserMapper {

   User selectUserByUsername(String username);

   void addUser(User user);

    User selectUserByPhone(String phone);

    User selectUserByUid(Long uid);

    void addUserByUid(User user);
}
