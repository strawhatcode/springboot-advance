package com.hat.consumer.controller;

import com.hat.dubbointerface.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Value("${server.port}")
    private String port;
//    @Reference(version = "1.0.0")  //引入已经注册的服务，指定版本号，与@Service版本号对应
//    @Reference(version = "1.0.0",loadbalance = "roundrobin") //使用轮询负载均衡
//    @Reference(version = "1.0.0",loadbalance = "leastactive") //使用最少活跃数负载均衡
    @Reference(version = "1.0.0",loadbalance = "consistenthash ", cluster = "failover")//使用一致性hash负载均衡
    UserService userService;

    @GetMapping("/getuser")
    public String getUserApi(){
            return "consumer["+port+"],use:"+userService.getUser();
        }
}
