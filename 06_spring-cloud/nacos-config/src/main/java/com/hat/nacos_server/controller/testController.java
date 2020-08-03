package com.hat.nacos_server.controller;

import com.hat.nacos_server.bean.configModel.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RefreshScope
public class testController {

//    @Value("${test.name}")
//    private String name;
//    @Value("${test.age}")
//    private String age;
    @Autowired
    private ConfigProperties properties;


    @GetMapping("t")
    public String t(){
        return properties.getName()+properties.getAge();
//        return name+age;
    }

    @GetMapping("tt")
    public String tt(){
        return properties.getAddress()+"-"+properties.getCity()+"-"
                +properties.getNum() + " "+properties.getPhone();
    }

    @GetMapping("share")
    public String testShare(){
        return properties.getShare().getLanguage()+"--"+properties.getShare().getLocation();
    }
}
