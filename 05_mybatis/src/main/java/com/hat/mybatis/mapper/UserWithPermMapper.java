package com.hat.mybatis.mapper;

import com.hat.mybatis.bean.UserWithPerms;

public interface UserWithPermMapper {
    UserWithPerms getUserWithPerm(String username);
}
