package com.wbiao.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.wbiao.user.feignService")
public class WbiaoOauthServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(WbiaoOauthServerApplication.class,args);
    }
}
