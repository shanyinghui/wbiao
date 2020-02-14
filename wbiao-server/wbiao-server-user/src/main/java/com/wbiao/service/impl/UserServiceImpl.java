package com.wbiao.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.wbiao.config.SmsProperties;
import com.wbiao.mapper.UserMapper;
import com.wbiao.service.UserService;
import com.wbiao.user.pojo.User;
import com.wbiao.util.NumberUtils;
import com.wbiao.util.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private SmsProperties smsProperties;


    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public void sendCode(String phone) throws ClientException {
        if(StringUtils.isBlank(phone)){
            return ;
        }
        //生成验证码
        String code = NumberUtils.generateCode(6);
        System.out.println(code);
        //发送短信
        smsUtil.sendSms(phone,code,smsProperties.getSignName(),smsProperties.getVerifyCodeTemplate());
        //存入redis
        redisTemplate.opsForValue().set("user:login:"+phone,code,5, TimeUnit.MINUTES);
    }

    @Override
    public User phoneLogin(String phone, String code)throws Exception {
        String redis_code = (String)redisTemplate.opsForValue().get("user:login:" + phone);
        if(code.equals(redis_code)){
            User user = userMapper.selectUserByPhone(phone);
            redisTemplate.delete("user:login:" + phone);
            return user;
        }else{
            throw new Exception("验证码输入错误，请重新输入");
        }

    }

    /**
     *  微博登录时，把登录信息保存到数据库
     * @param user
     */
    @Override
    public User addUser(User user) {
        User u = userMapper.selectUserByUid(user.getUid());
        //如果user是null，则代表是第一次采用微博登录
        if(u==null){
           userMapper.addUserByUid(user);
           u = userMapper.selectUserByUid(user.getUid());
           return u;
        }
        return u;
    }
}
