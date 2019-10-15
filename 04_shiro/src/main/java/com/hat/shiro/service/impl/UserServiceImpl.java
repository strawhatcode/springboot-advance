package com.hat.shiro.service.impl;

import com.hat.shiro.bean.User;
import com.hat.shiro.mapper.UserMapper;
import com.hat.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getPasswordByUsername(String username) {
        return userMapper.getPasswordByUsername(username);
    }
}
