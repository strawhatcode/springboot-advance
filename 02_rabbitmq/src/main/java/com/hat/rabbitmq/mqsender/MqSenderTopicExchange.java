package com.hat.rabbitmq.mqsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqSenderTopicExchange {
    private final static Logger log = LoggerFactory.getLogger(MqSenderTopicExchange.class);

    @Autowired
    AmqpTemplate rabbitTemplate;

    //发布消息,这里为了方便测试，把路由键也作为参数传过来
    public void SenderA(String msg,String routingkey){
        rabbitTemplate.convertAndSend("topicExchange",routingkey,msg);
        log.info("【SenderA】发布消息成功————"+msg);
    }


    public void SenderB(String msg, String routingkey){
        rabbitTemplate.convertAndSend("topicExchangeB",routingkey,msg);
        log.info("【SenderB】发布消息成功————"+msg);
    }
}
