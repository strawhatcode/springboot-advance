package com.hat.shiro.mapper;

import com.hat.shiro.bean.Perms;

import java.util.List;

public interface PermMapper {
    List<Perms> getPerm(String role);
}
