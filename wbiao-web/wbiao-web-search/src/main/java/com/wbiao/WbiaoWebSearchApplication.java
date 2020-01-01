package com.wbiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient()
@EnableFeignClients(basePackages = {"com.wbiao.search.feignService","com.wbiao.goods.feignService"})
public class WbiaoWebSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(WbiaoWebSearchApplication.class,args);
    }
}
