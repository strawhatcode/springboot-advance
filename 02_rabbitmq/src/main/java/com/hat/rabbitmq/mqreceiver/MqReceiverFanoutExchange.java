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
public class MqReceiverFanoutExchange {
    private static final Logger log = LoggerFactory.getLogger(MqReceiverFanoutExchange.class);

    //监听[fanoutQueueA]队列
    @RabbitListener(queues = "fanoutQueueA")
    @RabbitHandler
    public void ReceiverA(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(),true);
        log.info("[ReceiverA]接收到消息---["+new String(msg.getBody())+']');
    }

    //监听[fanoutQueueB]队列
    @RabbitListener(queues = "fanoutQueueB")
    @RabbitHandler
    public void ReceiverB(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(),true);

        log.info("[ReceiverB]接收到消息---["+new String(msg.getBody())+']');
    }

    //监听[fanoutQueueC]队列
    @RabbitListener(queues = "fanoutQueueC")
    @RabbitHandler
    public void ReceiverC(Message msg,Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(),true);
        log.info("[ReceiverC]接收到消息---["+new String(msg.getBody())+']');
    }

}
