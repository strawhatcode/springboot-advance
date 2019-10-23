package com.hat.mybatis.service.impl;

import com.hat.mybatis.bean.User;
import com.hat.mybatis.mapper.UserMapper;
import com.hat.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public List<User> getUserWithPerms(String username) {
        return userMapper.getUserWithPerms(username);
    }

    @Override
    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int insertUserList(List<User> users) {
        return userMapper.insertUserList(users);
    }
}
