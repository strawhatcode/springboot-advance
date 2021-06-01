package com.hat.nacos_serviceC.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceC")
public class ProviderCController {

    @Value("${server.port}")
    private String ip;

    @GetMapping("/provider")
    public String provider(String msg){
        return msg+"  访问ip：[ "+ip+" ]的providerC";
    }
}
