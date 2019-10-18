# Mybatis

## Mybatis介绍

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通 的Java 对象）为数据库中的记录。

## Mybatis的优缺点

### 优点：

- **使用连接池来连接数据库，减少了频繁的连接与关闭操作，与JDBC相比不用每个数据库操作都要写一堆重复的代码，减少性能消耗**
- **用xml文件编写sql代码使之与java类完全分开，便于维护和管理**
- **可以更方便使用动态sql**

### 缺点：

- **需要写许多sql语句代码**
- **sql语句非常依赖数据库，导致数据库移植性差，不可以轻易更改数据库**

## Mybati的工作原理和工作流程

mybatis根据配置文件(application.yml或者mybatis-config.xml)使用sqlsessionFactoryBuilder构建sqlsessionFactory，sqlsessionFactory根据openSession()获取sqlSession。sqlSession包含了执行sql语句的所有方法，可以通过sqlSession直接运行映射的sql语句，完成对数据的增删改查和事物的提交工作，用完之后关闭SQLSession。以下是工作原理流程图(从网上搬过来的)

![](images/1.png)

## springboot整合mybatis

### 1.实现mybatis

### 1.1 创建一个springboot项目且添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.hat</groupId>
    <artifactId>05_mybatis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>05_mybatis</name>
    <description>Demo project for Spring Boot with mybatis</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--jdbc依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>

        <!--mybatis依赖-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>

        <!--mysql依赖-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

项目目录结构如下：

![](images/2.jpg)



### 1.2 创建applicaton.yml配置

```yaml
spring:
  datasource:
    url: "jdbc:mysql://127.0.0.1:3306/testshiro?characterEncoding=utf-8&serverTimezone=UTC"
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
mybatis:
  mapper-locations: classpath:mapper/*.xml       #扫描mapper xml文件路径
  #别名的实体类与resultMap属性功能相似，
  #如果实体类里的属性名与数据库表里的字段名一样，可以不设置resultMap。否则必须使用redultMap。
  type-aliases-package: "com.hat.mybatis.bean"
  configuration:
    map-underscore-to-camel-case: true  #开启驼峰命名法，可以把mysql里有下划线的字段名转成驼峰命名，如user_nick转成userNick
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  #打印数据库语句日志
```

### 1.3 创建User实体

```java
package com.hat.mybatis.bean;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String userNick;


    public User() {
    }

    public User(Integer id, String username, String password, String role, String nick) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userNick = nick;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getNick() {
        return userNick;
    }

    public void setNick(String nick) {
        this.userNick = nick;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", nick='" + userNick + '\'' +
                '}';
    }
}

```

### 1.4 创建UserMapper接口

```java
package com.hat.mybatis.mapper;

import com.hat.mybatis.bean.User;

/**
 * mapper接口，只需创建一个接口，mapper xml文件会映射到此接口
 */
//@Mapper      //第一种扫描方法，当有许多mapper接口时建议在spring启动类中添加@MapperScan注解
public interface UserMapper {
    User getUserByUsername(String username);
}
```

> **注意：这些mapper接口需要被扫描，一种方法是在这些接口上面添加@mapper注解，不过当有很多mapper接口时就需要写很多@mapper注解。另一种方法是在spring启动类中添加@mapperScan("com.hat.mybatis.mapper")注解（参数是mapper接口文件的全路径），扫描该文件夹里所有mapper接口**

### 1.5 创建userMapper.xml映射文件(即写sql语句的文件)

```xml
<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace：必须，作用一：该命名空间可以把不同的语句区分开来，建议使用全路径名以防出现冲突
                     作用二：与mapper接口进行绑定，把sql语句映射到mapper接口中-->
<mapper namespace="com.hat.mybatis.mapper.UserMapper">
    <!--resultType：结果集的类型，查询结果的数据是什么类型-->
    <select id="getUserByUsername" resultType="User">
        select * from user where username=#{username}
    </select>
</mapper>

```

### 1.6 编写service层接口代码(UserService)和实现service接口代码(UserServiceImpl)

1. UserService

   ```java
   package com.hat.mybatis.service;
   
   import com.hat.mybatis.bean.User;
   
   public interface UserService {
       User getUserByUsername(String username);
   }
   ```

2. UserServiceImpl

   ```java
   package com.hat.mybatis.service.impl;
   
   import com.hat.mybatis.bean.User;
   import com.hat.mybatis.mapper.UserMapper;
   import com.hat.mybatis.service.UserService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   @Service
   public class UserServiceImpl implements UserService {
   
       @Autowired
       UserMapper userMapper;
   
       @Override
       public User getUserByUsername(String username) {
           return userMapper.getUserByUsername(username);
       }
   }
   ```

### 1.7 编写controller类UserController

```java
package com.hat.mybatis.controller;

import com.hat.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/{username}")
    public Object getUser(@PathVariable String username){
        //根据用户名查询该用户的信息并返回给前端
        return userService.getUserByUsername(username);
    }
}
```

### 1.8 springboot启动类添加mapper接口扫描注解

```java
package com.hat.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hat.mybatis.mapper") //扫描mapper接口注解，参数要用全路径名
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

```

### 1.9 启动项目使用postman测试

![](images/3.jpg)