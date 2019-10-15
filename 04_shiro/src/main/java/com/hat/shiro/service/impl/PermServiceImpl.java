package com.hat.shiro.service.impl;

import com.hat.shiro.bean.Perms;
import com.hat.shiro.mapper.PermMapper;
import com.hat.shiro.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermServiceImpl implements PermService {
    @Autowired
    PermMapper permMapper;

    @Override
    public List<Perms> getPerm(String role) {
        return permMapper.getPerm(role);
    }
}
