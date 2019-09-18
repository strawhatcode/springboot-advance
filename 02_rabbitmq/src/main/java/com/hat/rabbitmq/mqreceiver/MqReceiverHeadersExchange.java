package com.hat.rabbitmq.mqreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MqReceiverHeadersExchange {
    private static final Logger log = LoggerFactory.getLogger(MqReceiverHeadersExchange.class);

    @RabbitListener(queues = "headerQueueA")
    @RabbitHandler
    public void ReceiverAll(Message msg){
        log.info("[ Receiver（All）]收到消息---["+new String(msg.getBody())+"]");
    }

    @RabbitListener(queues = "headerQueueB")
    @RabbitHandler
    public void ReceiverAny(Message msg){
        log.info("[ Receiver（Any）]收到消息---["+new String(msg.getBody())+"]");
    }
}
