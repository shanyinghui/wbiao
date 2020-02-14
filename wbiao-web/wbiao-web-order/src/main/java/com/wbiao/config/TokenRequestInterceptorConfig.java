package com.wbiao.config;

import com.wbiao.util.TokenRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenRequestInterceptorConfig {

    @Bean
    public TokenRequestInterceptor tokenRequestInterceptor(){
        TokenRequestInterceptor tokenRequestInterceptor = new TokenRequestInterceptor();
        return tokenRequestInterceptor;
    }

}
