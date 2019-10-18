package com.hat.mybatis.service.impl;

import com.hat.mybatis.bean.User;
import com.hat.mybatis.mapper.UserMapper;
import com.hat.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
