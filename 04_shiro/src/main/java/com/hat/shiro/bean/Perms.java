package com.hat.shiro.bean;

public class Perms {
    private String role;
    private String perm;

    public Perms() {
    }

    public Perms(String role, String perm) {
        this.role = role;
        this.perm = perm;
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
        return "Perms{" +
                "role='" + role + '\'' +
                ", perm='" + perm + '\'' +
                '}';
    }
}
