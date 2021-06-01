package com.hat.nacos_serviceA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableDiscoveryClient //开启服务发现客户端
public class NacosServiceAApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosServiceAApplication.class, args);
    }
}
