package com.hat.mybatis.controller;

import com.hat.mybatis.bean.Permission;
import com.hat.mybatis.bean.User;
import com.hat.mybatis.bean.UserWithPerms;
import com.hat.mybatis.mapper.PermsMapper;
import com.hat.mybatis.service.PermsService;
import com.hat.mybatis.service.UserService;
import com.hat.mybatis.service.UserWithPermService;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Target;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PermsService permsService;
    @Autowired
    UserWithPermService userWithPermService;

    @RequestMapping("/user/{username}")
    @Transactional
    public Object getUser(@PathVariable String username){
        //根据用户名查询该用户的信息并返回给前端
        System.out.println(userService.getUserByUsername(username)+"[1111111]");
        User user = userService.getUserByUsername(username);
        System.out.println(user+"[2222222]");
        return user;
    }

    @RequestMapping("/user")
    @Transactional
    public Object getUserWithPerms(String username){
        User user = userService.getUserByUsername(username);
        List<Permission> permission = permsService.getPermsByUsername(user.getRole());
        user.setPermissions(permission);
        System.out.println(user);
        return user;
    }

    //修改权限的api
    @RequestMapping("/update/perm")
    @Transactional
    public int updatePerm(int id,String perm){
        int result = permsService.updatePerm(id,perm);
        return result;
    }

    //多表查询的api
    @RequestMapping("/getuser")
    @Transactional
    public Object getUserPerms(String username){
        UserWithPerms userWithPerms = userWithPermService.getUserWithPerm(username);
        System.out.println(userWithPerms);
        return userWithPerms;
    }

    @RequestMapping("/get")
    @Transactional
    public Object getUser2(String username){
        User user = userService.getUser(username);
        System.out.println(user);
        return user;
    }
}
