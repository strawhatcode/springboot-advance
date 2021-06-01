package com.hat.nacos_serviceD.feign;


import com.hat.nacos_serviceD.hystrix.ServiceDfallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "nacos-serviceC",fallback = ServiceDfallBack.class) // 调用的服务提供者名称
public interface FeignServiceD {

    // 服务提供者接口的uri（端口号后面的）
    @GetMapping(value = "/serviceC/provider")
    String getProviderD(@RequestParam(name = "msg") String msg); //要注意指定参数
}
