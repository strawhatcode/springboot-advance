package com.hat.shiro.service;

import com.hat.shiro.bean.Perms;

import java.util.List;

public interface PermService {
    List<Perms> getPerm(String role);
}
