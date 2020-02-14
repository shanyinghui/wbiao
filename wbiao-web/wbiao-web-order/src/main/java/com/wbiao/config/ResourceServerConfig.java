
package com.wbiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Configuration
@EnableResourceServer   //开启资源校验服务 -》令牌校验
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)//开启基于方法的安全认证机制
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //公钥
    private static final String PUBLIC_KEY = "public.key";

    @Bean
    public TokenStore tokenStore(){
        //jwt令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPublicKey());    //非对称加密，使用该公钥进行加密 
        return converter;
    }

    private String getPublicKey(){
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader is = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(is);
            return br.lines().collect(Collectors.joining("\n"));
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/cart","/css/**","/js/**","/img/**") //配置放行地址
                .permitAll()
                .anyRequest()       //其他地址需要认证才能访问
                .authenticated();
    }
}

