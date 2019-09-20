package com.hat.rabbitmq.mqreceiver;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MqReceiverDeadExchange {
    private final static Logger log = LoggerFactory.getLogger(MqReceiverDeadExchange.class);

    @RabbitListener(queues = "dead.queue")
    @RabbitHandler
    public void DeadLetterReceiver(Message msg, Channel channel) throws IOException {
        try {
            //消费normalQueue发送过来的消息
            log.info("【DeadLetterReceiver】收到死信消息---[{}]",new String(msg.getBody()));
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(msg.getMessageProperties().getDeliveryTag(),false,false);
        }
    }
}
