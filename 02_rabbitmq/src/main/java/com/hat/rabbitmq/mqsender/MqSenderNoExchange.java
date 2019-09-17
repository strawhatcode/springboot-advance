package com.hat.rabbitmq.mqsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //把该类交给spring容器管理
public class MqSenderNoExchange {
    private final static Logger log = LoggerFactory.getLogger(MqSenderNoExchange.class);

    //使用AmqpTemplate模版来发送消息
    @Autowired
    AmqpTemplate rabbitTemplate;

    /**
     * 不使用交换器的消息队列发送者
     */
    public void defaultQueueSender(String sendmsg){
        long begin = System.currentTimeMillis();
        log.info("【消息开始发送,消息内容为["+sendmsg+"]】");
        //使用convertAndSend来发送消息，
        // 参数： routingKey:路由键，用来接收此路由键的队列的消息，
        //        message：消息内容，内容可以是对象
        rabbitTemplate.convertAndSend("defaultQueue","[defaultQueue]发送的消息["+sendmsg+"]");
        log.info("【消息发送成功。。。。用时（"+(System.currentTimeMillis()-begin)+"ms）】");
    }
}
