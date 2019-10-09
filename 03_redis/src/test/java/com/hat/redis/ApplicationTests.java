package com.hat.redis;

import com.hat.redis.bean.User;
import com.hat.redis.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    RedisUtil redisUtil;
    @Test
    public void contextLoads() {
        User u = new User("wang","1241",22);

        System.out.println("-------------------------[");
        System.out.println(redisUtil.SUnion("set","set2","set3"));
        System.out.println("-------------------------[");
        System.out.println("-------------------------[");
        System.out.println(redisUtil.SIntersect("set","set2","set3"));

    }

}
