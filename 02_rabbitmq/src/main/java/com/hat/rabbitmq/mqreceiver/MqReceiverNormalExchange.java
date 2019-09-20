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
public class MqReceiverNormalExchange {
    private final static Logger log = LoggerFactory.getLogger(MqReceiverNormalExchange.class);
    int count = 0;
    @RabbitListener(queues = "normalQueue")
    @RabbitHandler
    public void NormalReceiver(Message msg, Channel channel) throws IOException {
        String message = new String(msg.getBody());
        //模拟出现异常重试且重试失败，重试失败后才把消息发送到死信队列
        if ("这是消息3".equals(message) || "这是消息8".equals(message)){
            count += 1;
            if (count <= 3){
                log.info("消息[{}]重试第[{}]次",message,count);
                channel.basicNack(msg.getMessageProperties().getDeliveryTag(),false,true);
            }else{
                log.info("消息[{}]重试次数已用完，丢弃消息",message);
                channel.basicNack(msg.getMessageProperties().getDeliveryTag(),false,false);
            }
        }else {
            log.info("【NormalReceiver】收到消息****[{}]",message);
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(),false);
            count = 0;
        }
    }
}
