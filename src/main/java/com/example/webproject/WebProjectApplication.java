package com.example.webproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.webproject")
public class WebProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebProjectApplication.class, args);
    }

}
