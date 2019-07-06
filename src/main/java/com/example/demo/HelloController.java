package com.example.demo;

import com.example.demo.mapper.UserLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloController {
    @Autowired
    private UserLoginMapper userLoginMapper;
    @RequestMapping("/hello")
    public String hello(Integer id,String password){

        return "hello";
    }


}
