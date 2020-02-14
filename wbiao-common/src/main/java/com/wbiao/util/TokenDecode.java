package com.wbiao.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TokenDecode {
    //公钥
    private static final String PUBLIC_KEY = "public.key";

    private static String getPublicKey(){
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader is = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(is);
            return br.lines().collect(Collectors.joining("\n"));
        }catch (Exception e){
            return null;
        }
    }

    /**
     *  读取令牌信息
     * @param token
     * @return
     */
    private static String decode(String token){
        //校验jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getPublicKey()));

        //获取jwt的内容
        String claims = jwt.getClaims();
        return claims;
    }

    public static String getUserinfo(){
        //获取授权信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //令牌解码
        return decode(details.getTokenValue());
    }


}
