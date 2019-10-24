package com.hat.mybatis.service;

import com.hat.mybatis.bean.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);
    List<User> getUserWithPerms(String role);
    User getUser(String username);
    int insertUser(User user);
    int insertUserList(List<User> users);

    List<User> findByChoose(User user);
    List<User> findByIf(User user);

    int updateBySet(User user);

}
