package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @program: cloud
 * @description:
 * @author: 林开颜
 * @create: 2023/6/29 10:20
 */
@SpringBootApplication
@EnableEurekaClient
public class UserCenterApplication {
    public static void main(String[] args){
        SpringApplication.run(UserCenterApplication.class,args);
    }
}
