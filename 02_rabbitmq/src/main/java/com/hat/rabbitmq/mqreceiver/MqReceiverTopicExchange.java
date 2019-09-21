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
public class MqReceiverTopicExchange {
    private final static Logger log = LoggerFactory.getLogger(MqReceiverTopicExchange.class);

    //消息消费者，监听topicQueueA队列
    @RabbitListener(queues = "topicQueueA")
    @RabbitHandler
    public void ReceiverA(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(),false);
        log.info("thread【"+Thread.currentThread().getId()+"】[ReceiverA]接收到消息-----["+new String(msg.getBody())+"]");
    }

    @RabbitListener(queues = "topicQueueB")
    @RabbitHandler
    public void ReceiverB(String msg){
        log.info("[ReceiverB]接收到消息-----["+msg+"]");
    }
}
