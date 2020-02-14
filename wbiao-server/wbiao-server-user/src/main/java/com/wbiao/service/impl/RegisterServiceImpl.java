package com.wbiao.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.wbiao.config.SmsProperties;
import com.wbiao.mapper.UserMapper;
import com.wbiao.service.RegisterService;
import com.wbiao.user.pojo.User;
import com.wbiao.util.NumberUtils;
import com.wbiao.util.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void sendCode(String phone)throws ClientException{

        if(StringUtils.isBlank(phone)){
           return ;
        }
        //生成验证码
        String code = NumberUtils.generateCode(6);
        System.out.println(code);
        //发送短信
        smsUtil.sendSms(phone,code,smsProperties.getSignName(),smsProperties.getVerifyCodeTemplate());
        //存入redis
        redisTemplate.opsForValue().set("user:register:"+phone,code,5, TimeUnit.MINUTES);

    }

    @Override
    public void register(User user, String code)throws Exception {
        //验证验证码
        String redis_code  = (String)redisTemplate.opsForValue().get("user:register:" + user.getPhone());
        if(code.equals(redis_code)){
            //密码加密
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder ();
            String password = bCrypt.encode(user.getPassword());
            user.setPassword(password);
            user.setCreated(new Timestamp(new Date().getTime()));
            user.setUpdated(new Timestamp(new Date().getTime()));
            user.setState("1");
            //添加用户
            userMapper.addUser(user);
            //删除redis中的数据
            redisTemplate.delete("user:register:" + user.getPhone());
        }else{
            throw new Exception("手机验证码输入错误，请重新输入");
        }
    }

    @Override
    public User validateUsername(String username) {
        User user = userMapper.selectUserByUsername(username);
        return user;
    }

    @Override
    public User phone(String phone) {
        User user = userMapper.selectUserByPhone(phone);
        return user;
    }
}
