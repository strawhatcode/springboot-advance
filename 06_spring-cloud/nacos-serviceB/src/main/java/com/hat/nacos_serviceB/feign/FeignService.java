package com.hat.nacos_serviceB.feign;


import com.hat.nacos_serviceB.hystrix.ServiceAfallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "nacos-serviceA",fallback = ServiceAfallBack.class) // 调用的服务提供者名称
public interface FeignService {

    // 服务提供者接口的uri（端口号后面的）
    @GetMapping(value = "/serviceA/provider")
    String getProvider(@RequestParam(name = "msg") String msg); //要注意指定参数
}
