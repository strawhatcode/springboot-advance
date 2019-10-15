package com.hat.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class shiroController {

    @RequestMapping("/login")
    public Object login(String username, String password){
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        //判断是否已经认证过，认证过则不再重复认证
        if (!subject.isAuthenticated()) {
            //创建token
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //设置记住我，下次不用登录即可访问，
            token.setRememberMe(true);
            try {
                //把token交给Subject认证是否登录成功
                subject.login(token);
            } catch (UnknownAccountException e) {  //帐号不存在时抛出的异常
                e.printStackTrace();
                return "认证失败，帐号不存在";
            } catch (IncorrectCredentialsException e) {  //密码错误时抛出的异常
                e.printStackTrace();
                return "认证失败，密码错误";
            } catch (LockedAccountException e) {   //账户被锁定时抛出的异常
                e.printStackTrace();
                return "认证失败，账户被锁定";
            } catch (AuthenticationException e) {  //以上异常的父类
                e.printStackTrace();
                return "登录失败";
            }
        }
        return "进入登录页面";
    }

    @RequestMapping("/login/aa")
    public Object loginaa(){
        return "进入/login/aa页面";
    }

    @RequestMapping("/main")
    public Object main(){
        return "进入main页面";
    }

    @RequestMapping("/main/bb")
    public Object mainbb(){
        return "进入/main/bb页面";
    }

    @RequestMapping("/user")
    public Object user(){
        return "进入需要user角色的user页面";
    }

    @RequestMapping("/manager")
    public Object manager(){
        return "进入需要manager角色的manager页面";
    }

    @RequestMapping("/perm_a")
    public Object perm_a(){
        return "进入需要perm_a权限的perm_a页面";
    }

    @RequestMapping("/perm_b")
    public Object perm_b(){
        return "进入需要perm_b权限的perm_b页面";
    }

    @RequestMapping("/unauthorized")
    public Object unauthorized(){
        return "无权限进入页面";
    }

}
