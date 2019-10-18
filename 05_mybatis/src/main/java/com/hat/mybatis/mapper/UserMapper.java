package com.hat.mybatis.mapper;

import com.hat.mybatis.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper接口，只需创建一个接口，mapper xml文件会映射到此接口
 */
//@Mapper
public interface UserMapper {
    User getUserByUsername(String username);
}
