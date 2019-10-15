package com.hat.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hat.shiro.mapper") //扫描mapper映射的路径
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
