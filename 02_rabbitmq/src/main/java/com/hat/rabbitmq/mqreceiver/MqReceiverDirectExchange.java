package com.hat.rabbitmq.mqreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;



@Component
public class MqReceiverDirectExchange {
    private final static Logger log = LoggerFactory.getLogger(MqReceiverDirectExchange.class);

    @RabbitListener(queues = "directQueueA")
    @RabbitHandler
    public void ReceiverA(Message msg){
        String getQueueName = msg.getMessageProperties().getConsumerQueue();
        String massage = new String(msg.getBody());
        log.info("【ReceiverA】接收到来自队列[ "+getQueueName+" ]的信息---->[ "+massage+" ]");
    }

    @RabbitListener(queues = {"directQueueB","directQueueA"})
    @RabbitHandler
    public void ReceiverB(Message msg){ //接收Message类型的返回值(包括消息队列的一些信息)
        //获取当前消息是从哪个队列中获取的
        String getQueueName = msg.getMessageProperties().getConsumerQueue();
        //获取当前消费的消息内容，获取到的是byte类型，要转成String
        String massage = new String(msg.getBody());
        log.info("【ReceiverB】接收到来自队列[ "+getQueueName+" ]的信息---->["+massage+"]");
    }
}
