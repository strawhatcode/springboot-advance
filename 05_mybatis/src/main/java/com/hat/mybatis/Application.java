package com.hat.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hat.mybatis.mapper") //扫描mapper接口注解，参数要用全路径名
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
