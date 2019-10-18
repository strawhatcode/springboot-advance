package com.hat.mybatis.service;

import com.hat.mybatis.bean.User;

public interface UserService {
    User getUserByUsername(String username);
}
