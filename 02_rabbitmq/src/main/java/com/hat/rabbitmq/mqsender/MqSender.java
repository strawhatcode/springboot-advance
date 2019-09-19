package com.hat.rabbitmq.mqsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MqSender {
    private final static Logger log = LoggerFactory.getLogger(MqSender.class);

    @Autowired
    RabbitTemplate rabbitTemplate; //这里使用RabbitTemplate来发送消息

    public void Sender(String exchange,String routingkey,String msg){
        //实例化一个CorrelationData对象充当消息的唯一id
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //发布消息
        rabbitTemplate.convertAndSend(exchange,routingkey,msg,correlationData);
//        log.info("【Sender】发送的消息内容[{}]到[{}]交换机，路由键为[{}]，消息id为[{}]",
//                msg,exchange,routingkey,correlationData);
        log.info("【Sender】发送的消息内容[{}]",msg);
        log.info("【Sender】发送的交换机[{}]，",exchange);
        log.info("【Sender】发送的路由键[{}]，",routingkey);
        log.info("【Sender】发送的消息id为[{}]",correlationData);
    }

}
