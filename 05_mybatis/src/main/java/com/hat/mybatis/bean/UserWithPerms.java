package com.hat.mybatis.bean;

import java.io.Serializable;

public class UserWithPerms implements Serializable {
    private int id;
    private String username;
    private String password;
    private String userNick;
    private String role;
    private String perm;

    public UserWithPerms() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserWithPerms{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userNick='" + userNick + '\'' +
                ", role='" + role + '\'' +
                ", perm='" + perm + '\'' +
                '}';
    }
}
