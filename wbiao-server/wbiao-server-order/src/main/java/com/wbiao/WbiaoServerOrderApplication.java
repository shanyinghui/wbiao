package com.wbiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@MapperScan("com.wbiao.mapper")
@EnableFeignClients(basePackages = {"com.wbiao.goods.feignService","com.wbiao.pay.feignService"})
public class WbiaoServerOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(WbiaoServerOrderApplication.class,args);
    }
}
