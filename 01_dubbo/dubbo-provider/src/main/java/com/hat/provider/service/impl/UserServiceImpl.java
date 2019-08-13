package com.hat.provider.service.impl;

import com.hat.dubbointerface.bean.User;
import com.hat.dubbointerface.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component   //把当前类交给spring管理
@Service(version = "1.0.0",weight = 200)//版本号：1.0.0，权重：200，这里是dubbo的Service，不是spring的Service，把该服务放到注册中心
//@Service(version = "1.0.0",weight = 100)
public class UserServiceImpl implements UserService{

    @Value("${server.port}")
    private String port;

    @Override
    public String getUser() {
        User user = new User();
        user.setName("张三");
        user.setAge(20);
        user.setPhone("12345678912");
        return "this is provider ["+port+"],message:"+user.toString();
    }

}
