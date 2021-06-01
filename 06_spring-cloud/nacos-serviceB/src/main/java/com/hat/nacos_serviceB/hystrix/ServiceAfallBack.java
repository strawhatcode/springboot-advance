package com.hat.nacos_serviceB.hystrix;

import com.hat.nacos_serviceB.feign.FeignService;
import org.springframework.stereotype.Component;

@Component
public class ServiceAfallBack implements FeignService {
    @Override
    public String getProvider(String msg) {
        return "进入熔断方法；msg："+msg;
    }
}
