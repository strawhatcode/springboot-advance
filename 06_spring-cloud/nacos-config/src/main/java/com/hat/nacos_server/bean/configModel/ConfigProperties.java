package com.hat.nacos_server.bean.configModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component    //注入到spring容器
@Setter    //lombok的set
@Getter    //lombok的get
@ConfigurationProperties(prefix = "test")   //映射application.yml配置文件里前缀是test的属性
public class ConfigProperties {
    private String name;
    private String age;
    // 测试多配置文件时新增的属性
    private String address;
    private String city;
    private String phone;
    private String num;
    // 测试共享配置
    private ShareProperties share;
}
