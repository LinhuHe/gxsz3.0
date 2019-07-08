package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;
@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {

    public static void main(String[] args) {
 	TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(DemoApplication.class, args);
    }

}
