package com.hat.mybatis.service.impl;

import com.hat.mybatis.bean.Permission;
import com.hat.mybatis.mapper.PermsMapper;
import com.hat.mybatis.service.PermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermsServiceImpl implements PermsService {
    @Autowired
    PermsMapper permsMapper;
    @Override
    public List<Permission> getPermsByUsername(String role) {
        return permsMapper.getPermsByUsername(role);
    }

    //这里第一个参数是权限表中的id，perm是修改后的权限
    @Override
    public Integer updatePerm(int p_id,String perm) {
        return permsMapper.updatePerm(p_id,perm);
    }
}
