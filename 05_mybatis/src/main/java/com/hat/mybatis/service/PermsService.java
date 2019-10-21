package com.hat.mybatis.service;

import com.hat.mybatis.bean.Permission;

import java.util.List;

public interface PermsService {
    List<Permission> getPermsByUsername(String role);
    Integer updatePerm(int p_id,String perm);
}
