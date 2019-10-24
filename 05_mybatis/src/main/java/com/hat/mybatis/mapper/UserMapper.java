package com.hat.mybatis.mapper;

import com.hat.mybatis.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper接口，只需创建一个接口，mapper xml文件会映射到此接口
 */
//@Mapper
public interface UserMapper {
    User getUserByUsername(String username);
    List<User> getUserWithPerms(String username);

    @Select("select * from user where username = #{username}")
    User getUser(String username);

    int insertUser(@Param("user") User user);

    int insertUserList(List<User> users);

    List<User> findByChoose(User user);
    List<User> findByIf(User user);

    int updateBySet(User user);
}


