package com.hat.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class redisConfiguration {

    /**
     * 重写redisTemplate类，解决序列化问题
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        //实例化RedisTemplate
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        //给RedisTemplate设置redis连接工厂
        template.setConnectionFactory(connectionFactory);

        //提供一些功能将转换成Java对象匹配JSON结构
        ObjectMapper om = new ObjectMapper();
        //指定要检测的范围，参数1：所有域(set、get),参数2：所有类型修饰符(private到public)
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //指定序列化的类型，参数：非final修饰的类型
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        //实例化Jackson2Json序列化
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        //给Jackson2Json设置自定义的ObjectMapper属性
        serializer.setObjectMapper(om);

        //redis的string类型的key值序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        //redis的string类型的value值序列化方式
        template.setValueSerializer(serializer);

        //redis的hash类型的key值序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        //redis的hash类型的value值序列化方式
        template.setHashValueSerializer(serializer);
        return template;
    }
}
