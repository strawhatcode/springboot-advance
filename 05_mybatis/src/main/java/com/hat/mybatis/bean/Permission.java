package com.hat.mybatis.bean;

import java.io.Serializable;

public class Permission implements Serializable {
    private int id;
    private String role;
    private String perm;

    public Permission() {
        super();
    }

    public Permission(int id, String role, String perm) {
        this.id = id;
        this.role = role;
        this.perm = perm;

    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", perm='" + perm + '\'' +
                '}';
    }
}
