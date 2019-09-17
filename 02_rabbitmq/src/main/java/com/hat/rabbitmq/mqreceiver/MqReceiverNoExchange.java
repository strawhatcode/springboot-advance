package com.hat.rabbitmq.mqreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component //把该类交给spring容器管理
public class MqReceiverNoExchange {
    private final static Logger log = LoggerFactory.getLogger(MqReceiverNoExchange.class);

    @RabbitListener(queues = "defaultQueue")  //监听路由键为[defaultQueue]的队列
    @RabbitHandler  //收到消息后执行下面方法
    public void getDefaultQueueMsg(String msg){ //参数类型，发送者什么类型就什么类型
        log.info("[getDefaultQueueMsg（1）]接收到--->"+msg);
    }

    @RabbitListener(queues = "defaultQueue")  //监听路由键为[defaultQueue]的队列
    @RabbitHandler  //收到消息后执行下面方法
    public void getDefaultQueueMsg2(String msg){ //参数类型，发送者什么类型就什么类型
        log.info("[getDefaultQueueMsg（2）]接收到--->"+msg);
    }





}
