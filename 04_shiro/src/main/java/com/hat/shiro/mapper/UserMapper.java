package com.hat.shiro.mapper;

import com.hat.shiro.bean.User;

public interface UserMapper {
    User getPasswordByUsername(String username);
}
