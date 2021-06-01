package com.hat.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GatewayController {

    @RequestMapping(value = "/fallback",method = {RequestMethod.POST,RequestMethod.GET})
    public String fallbackGateway(){
        return "进入到gateway的fallback接口";
    }
}
