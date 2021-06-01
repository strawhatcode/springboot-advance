package com.hat.nacos_serviceD.hystrix;

import com.hat.nacos_serviceD.feign.FeignServiceD;
import org.springframework.stereotype.Component;

@Component
public class ServiceDfallBack implements FeignServiceD {
    @Override
    public String getProviderD(String msg) {
        return "进入ServiceD熔断方法";
    }
}
