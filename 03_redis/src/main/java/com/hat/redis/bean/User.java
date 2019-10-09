package com.hat.redis.bean;

public class User {
    private String name;
    private String phpne;
    private int age;

    public User(String name, String phpne, int age) {
        this.name = name;
        this.phpne = phpne;
        this.age = age;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhpne() {
        return phpne;
    }

    public void setPhpne(String phpne) {
        this.phpne = phpne;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phpne='" + phpne + '\'' +
                ", age=" + age +
                '}';
    }
}
