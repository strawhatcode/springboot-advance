package com.hat.rabbitmq.mqreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MqReceiverFanoutExchange {
    private static final Logger log = LoggerFactory.getLogger(MqReceiverFanoutExchange.class);

    //监听[fanoutQueueA]队列
    @RabbitListener(queues = "fanoutQueueA")
    @RabbitHandler
    public void ReceiverA(String msg){
        log.info("[ReceiverA]接收到消息---["+msg+']');
    }

    //监听[fanoutQueueB]队列
    @RabbitListener(queues = "fanoutQueueB")
    @RabbitHandler
    public void ReceiverB(String msg){
        log.info("[ReceiverB]接收到消息---["+msg+']');
    }

    //监听[fanoutQueueC]队列
    @RabbitListener(queues = "fanoutQueueC")
    @RabbitHandler
    public void ReceiverC(String msg){
        log.info("[ReceiverC]接收到消息---["+msg+']');
    }

}
