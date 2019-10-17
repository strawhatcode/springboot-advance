package com.hat.shiro.config;

import com.hat.shiro.bean.Perms;
import com.hat.shiro.bean.User;
import com.hat.shiro.service.PermService;
import com.hat.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

//自定义继承AuthorizingRealm的CustomRealm类并重写两个方法
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    PermService permService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入授权方法");
        //实例化SimpleAuthorizationInfo类
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取主体(Subject)的信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        //获取该用户角色的所有权限
        List<Perms> perms = permService.getPerm(user.getRole());
        //用来存放权限的set列表
        Set<String> set_perm = new HashSet<>();
        Set<String> set_role = new HashSet<>();
        set_role.add(user.getRole());
        //遍历权限存入set_perm
        for (Perms perm : perms){
            set_perm.add(perm.getPerm());
        }
        //添加角色
        info.addRole(user.getRole());
        //添加权限
        info.addStringPermissions(set_perm);
        //返回授权信息实例
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进入认证方法");
        //获取用户的token信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据帐号查询对应密码
        User user = userService.getPasswordByUsername(token.getUsername());
        //实例化SimpleAuthenticationInfo，参数:
        //      principle：主体，相当于当前用户，传入user对象
        //      credentials/hashedCredentials：密码/hash后的密码(使用加密密码时使用)
        //      credentialsSalt：密码加密的盐值，可以不用
        //      realmName：使用的realm的名字，自定义的CustomRealm类名
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),
                                                                    ByteSource.Util.bytes("salt"),getName());

        //返回一个认证信息实例
        return info;
    }
}
