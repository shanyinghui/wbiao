package com.wbiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.wbiao.mapper")
public class WbiaoServerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(WbiaoServerUserApplication.class,args);
    }
}
