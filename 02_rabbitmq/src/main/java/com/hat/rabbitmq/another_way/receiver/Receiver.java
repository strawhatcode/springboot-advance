package com.hat.rabbitmq.another_way.receiver;

import com.hat.rabbitmq.another_way.sender.Sender;
import com.hat.rabbitmq.another_way.utils.ConnectionUtil;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {
    private final static Logger log = LoggerFactory.getLogger(Sender.class);
    //声明队列名称、交换机名称和路由键
    private String QUEUE_NAME = "another.directQueue";
    private String EXCHANGE_NAME = "another.directExchange";
    private String ROUTING_KEY = "another.direct.routingkey";
    private int count = 0;
    public void directReceiver() throws IOException, TimeoutException {
        //创建一个连接
        Connection conn = ConnectionUtil.connectionFactory();
        //创建一个通道
        Channel channel = conn.createChannel();

        //如果这三个在发布者那里已经创建了可以不写,但是一般都写上
        //创建一个队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //创建一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true);
        //把队列与交换机绑定起来
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);

        //创建一个消费者实例
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body);
                if ("消息4".equals(msg)) {
                    //假如获取到【消息4】时发生异常重试5次然后丢弃消息
                    count += 1;
                    if (count <= 5){
                        //如果重试次数小于等于5则把消息重新入队
                        channel.basicNack(envelope.getDeliveryTag(),false,true);
                        log.info("【directReceiver】消息--【 {} 】第【{}】次重试",msg,count);
                    }else {
                        //重试次数大于5则丢弃消息
                        channel.basicNack(envelope.getDeliveryTag(),false,false);
                        log.info("【directReceiver】丢弃消息--【 {} 】",msg);
                        count = 0;
                    }

                }else {
                    log.info("【directReceiver】接收到信息--【 {} 】",msg);
                    //手动确认
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //消费者监听相应队列，autoAck：开启手动确认
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Receiver r = new Receiver();
        r.directReceiver();
    }
}
