package com.hat.mybatis.service.impl;

import com.hat.mybatis.bean.UserWithPerms;
import com.hat.mybatis.mapper.UserWithPermMapper;
import com.hat.mybatis.service.UserWithPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWithPermServiceImpl implements UserWithPermService {
    @Autowired
    UserWithPermMapper userWithPermMapper;
    @Override
    public UserWithPerms getUserWithPerm(String username) {
        return userWithPermMapper.getUserWithPerm(username);
    }
}
