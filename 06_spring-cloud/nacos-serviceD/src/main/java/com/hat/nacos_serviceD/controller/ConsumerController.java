package com.hat.nacos_serviceD.controller;

import com.hat.nacos_serviceD.feign.FeignServiceD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ConsumerController {
    @Autowired
    FeignServiceD feignService;  // 注入刚才声明的FeignService接口

//    @GetMapping("/getByFeignD")
    @RequestMapping(value = "/getByFeignD",method = {RequestMethod.POST,RequestMethod.GET}) //改成GET/POST请求均可以
    public String getByFeign(String msg){
        return feignService.getProviderD(msg);
    }
}
