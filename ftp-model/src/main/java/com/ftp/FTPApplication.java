package com.ftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @program: Default (Template) Project
 * @description: ${description}
 * @author: 林开颜
 * @create: 2023/9/11 17:20
 */
@SpringBootApplication
@EnableEurekaClient
public class FTPApplication {
    public static void main(String[] args) {
        SpringApplication.run(FTPApplication.class,args);
    }
}