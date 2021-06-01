package com.hat.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class GatewayConfig {

    @Bean("myKeyResolver")
    public MyKeyResolver myRateLimitFilter(){
        return new MyKeyResolver();
    }

    //    @Bean
//    public RouteLocator modifyRemoteAddr(RouteLocatorBuilder builder){
//        RemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);
//        return builder.routes().route("predicate-test",
//                predicateSpec -> predicateSpec.remoteAddr(resolver,"99.11.52.0/24")
//        .uri("http://localhost:2001")).build();
//
//    }
}
