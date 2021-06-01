package com.hat.nacos_serviceB;

import com.hat.nacos_serviceB.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients  //开启feign客户端
@RibbonClient(name = "random", configuration = RibbonConfig.class)
@EnableHystrix  //启动断路器
public class NacosServiceBApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosServiceBApplication.class, args);
    }

}