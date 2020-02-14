package com.wbiao.oauth.service.impl;

import com.wbiao.oauth.service.UserLoginService;
import com.wbiao.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     *  登录的实现
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @param grant_type
     * @return
     */
    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret, String grant_type)throws Exception{
        String url = "http://WBIAO-USER-AUTH/oauth/token";
        //请求提交数据的封装
        MultiValueMap<String,String> parameterMap = new LinkedMultiValueMap<String,String>();

        if(grant_type.equals("password")){
            parameterMap.add("username",username);
            parameterMap.add("password",password);
        }
        if(grant_type.equals("sms_code")) {
            parameterMap.add("phone",username);
            parameterMap.add("code",password);
        }
        if(grant_type.equals("wbLogin")){
            parameterMap.add("access_token",username);
            parameterMap.add("uid",password);
        }
        /*if(grant_type.equals("password")){
            parameterMap.add("username",username);
            parameterMap.add("password",password);
        }else{
            parameterMap.add("phone",username);
            parameterMap.add("code",password);
        }*/
        parameterMap.add("grant_type",grant_type);

        //请求头信息的封装
        MultiValueMap<String,String> headerMap = new LinkedMultiValueMap<String,String>();
        String Authorization = "Basic "+new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()),"UTF-8");

        headerMap.add("Authorization",Authorization);

        //创建HttpEntity对象，封装了请求头和请求体
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(parameterMap, headerMap);

        /**
         *  1、请求的url
         *  2、请求的方式
         *  3、requestEntity：请求信息的封装
         *  4、responseType：返回数据的类型
         */
        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);

        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String)exchange.getBody().get("access_token"));
        authToken.setRefreshToken((String)exchange.getBody().get("refresh_token"));
        authToken.setJti((String)exchange.getBody().get("jti"));
        return authToken;
    }
}
