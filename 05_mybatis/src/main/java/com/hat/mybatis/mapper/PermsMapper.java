package com.hat.mybatis.mapper;

import com.hat.mybatis.bean.Permission;

import java.util.List;

public interface PermsMapper {
    //根据角色查找权限
    List<Permission> getPermsByUsername(String role);
    //根据p_id修改权限
    Integer updatePerm(int p_id, String perm);
}
