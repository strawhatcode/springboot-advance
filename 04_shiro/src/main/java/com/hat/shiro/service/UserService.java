package com.hat.shiro.service;

import com.hat.shiro.bean.User;

public interface UserService {
    User getPasswordByUsername(String username);
}
