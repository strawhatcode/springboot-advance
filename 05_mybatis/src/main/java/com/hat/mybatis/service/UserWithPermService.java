package com.hat.mybatis.service;

import com.hat.mybatis.bean.UserWithPerms;

public interface UserWithPermService {
    UserWithPerms getUserWithPerm(String username);
}
