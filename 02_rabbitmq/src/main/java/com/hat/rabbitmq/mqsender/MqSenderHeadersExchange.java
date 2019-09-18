package com.hat.rabbitmq.mqsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqSenderHeadersExchange {
    private static final Logger log = LoggerFactory.getLogger(MqSenderHeadersExchange.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void Sender(String body, MessageProperties properties){
        Message msg = new Message(body.getBytes(), properties);
        rabbitTemplate.convertAndSend("headersExchange","",msg);
        log.info("【Headers Sender】使用的header为[ "+properties.getHeaders()+ "],发布的消息---["+body+"]");
    }
}
