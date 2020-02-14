package com.wbiao.oauth.expandLoginConfig;

import com.alibaba.fastjson.JSONObject;
import com.wbiao.oauth.util.UserJwt;
import com.wbiao.user.feignService.UserFeign;
import com.wbiao.user.pojo.User;
import com.wbiao.util.HttpClient;
import com.wbiao.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomUserDetailsService {
    @Autowired
    UserFeign userFeign;

    public UserDetails loadUserByPhoneAndSmsCode(String phone, String smsCode) {

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsCode)) {
            return null;
        }
        ResultUtil<com.wbiao.user.pojo.User> resultUtil = userFeign.phoneLogin(phone, smsCode);
        if(resultUtil.getCode().equals(500)){
            return null;
        }
        com.wbiao.user.pojo.User user = (com.wbiao.user.pojo.User)resultUtil.getData();
        // 判断成功后返回用户细节
        UserJwt userDetails = new UserJwt(user.getUsername(),user.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        return userDetails;
    }

    public UserDetails loadUserByAccess_token(String access_token,String uid) {
        try {
            if (StringUtils.isEmpty(access_token) || StringUtils.isEmpty(uid)) {
                return null;
            }
            String url = "https://api.weibo.com/2/users/show.json?access_token="+access_token+"&uid="+uid;
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);
            httpClient.get();

            //返回的登录的微博用户信息
            String content = httpClient.getContent();
            Map<String,Object> map = JSONObject.parseObject(content, Map.class);
            User user = new User();
            String username = UUID.randomUUID().toString();
            username = username.replace("-", "");
            user.setUsername("wb_"+username);
            user.setNick_name((String)map.get("screen_name")); //用户昵称
            user.setHead_pic((String)map.get("profile_image_url")); //用户头像地址
            String gender = (String)map.get("gender"); //用户的性别
            if(gender.equals("m")){
                user.setSex("1");
            }else {
                user.setSex("0");
            }
            user.setCreated(new Timestamp(new Date().getTime()));
            user.setUpdated(new Timestamp(new Date().getTime()));
            user.setState("1");
            user.setUid((Long)map.get("id")); //用户微博的唯一id
            user.setLogin_type(1); //登录方式 ：1是微博登录

            //把登录的微博用户信息保存到数据库
            ResultUtil<User> resultUtil = userFeign.addUserByUid(user);
            if(resultUtil.getCode().equals(500)){
                return null;
            }
            User u = (User)resultUtil.getData();

            // 判断成功后返回用户细节
            /*return new org.springframework.security.core.userdetails.User(user.getUsername(), "", AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
            UserJwt userDetails = new UserJwt(u.getUsername(),"",AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            return userDetails;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
