package com.hat.mybatis.controller;

import com.hat.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/{username}")
    public Object getUser(@PathVariable String username){
        //根据用户名查询该用户的信息并返回给前端
        return userService.getUserByUsername(username);
    }
}
