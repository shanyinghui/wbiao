package com.wbiao.user.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceFeignConfiguration {

    private int connectTimeout = 60000;

    private int readTimeout = 60000;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, readTimeout);
    }
}

