package com.wbiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WbiaoEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WbiaoEurekaServerApplication.class,args);
    }
}
