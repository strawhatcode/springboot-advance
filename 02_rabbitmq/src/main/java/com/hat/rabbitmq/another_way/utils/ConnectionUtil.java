package com.hat.rabbitmq.another_way.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//创建一个连接工具，以便生产者和消费者连接rabbitmq用
public class ConnectionUtil {
    public static Connection connectionFactory() throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置一些连接属性，与用yml配置连接的属性一样
        factory.setHost("192.168.229.130");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        //创建一个连接
        Connection con = factory.newConnection();
        return con;
    }
}
