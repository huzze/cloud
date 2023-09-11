package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: cloud
 * @description: 测试控制层
 * @author: 林开颜
 * @create: 2023/9/11 17:05
 */
@RestController
@RequestMapping("/auth")
public class TestController {
    @GetMapping("/hello")
    public String sayHello(){
        return "hello security";
    }
}
