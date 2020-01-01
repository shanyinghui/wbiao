package com.wbiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.wbiao.goods.feignService")
@EnableElasticsearchRepositories(basePackages = "com.wbiao.mapper")
public class WbiaoServerSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(WbiaoServerSearchApplication.class,args);
    }
}
