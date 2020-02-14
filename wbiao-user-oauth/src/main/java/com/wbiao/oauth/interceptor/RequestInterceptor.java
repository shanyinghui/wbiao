package com.wbiao.oauth.interceptor;

import com.alibaba.fastjson.JSONObject;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RequestInterceptor implements feign.RequestInterceptor {
    //在调用feign接口之前执行该拦截器
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //加载证书
        ClassPathResource resource = new ClassPathResource("wanbiao.jks");

        //读取证书数据
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(resource,"wanbiao".toCharArray());

        //获取证书中的公钥和私钥
        KeyPair keyPair = factory.getKeyPair("wanbiao","wanbiao".toCharArray());
        //获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        //创建令牌
        Map<String,Object> payload = new HashMap<>();
        payload.put("username","admin");
        payload.put("nickname","admin");
        payload.put("address","beijing");
        payload.put("authorities",new String[]{"admin","auth"});
        Jwt jwt = JwtHelper.encode(JSONObject.toJSONString(payload),new RsaSigner(privateKey));
        String token = jwt.getEncoded();

        requestTemplate.header("Authorization","Bearer"+token);
    }
}
