package com.hat.rabbitmq.mqsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MqSenderDirectExchange {
    private final static Logger log = LoggerFactory.getLogger(MqSenderDirectExchange.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    //生产者A
    public void SenderA(String msg){
        /**
         *  参数:
         *      exchange：交换机名称
         *      routingKey：路由键
         *      message：消息内容
         */
        rabbitTemplate.convertAndSend("directExchange","directA",msg);
        log.info("【SenderA】发送消息["+msg+"]");
    }

    //生产者B
    public void SenderB(String msg){
        rabbitTemplate.convertAndSend("directExchange","directB",msg);
        log.info("【SenderB】发送消息["+msg+"]");
    }
}
