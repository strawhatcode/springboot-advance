package com.hat.rabbitmq.controller;

import com.hat.rabbitmq.mqsender.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderController {
    @Autowired
    MqSender sender;
    //测试一次请求发送10条消息到指定交换机的指定队列
    @GetMapping("/send")
    public String send(String exchange,
                       String routingkey,
                       String msg){
        for(int i=1; i<10;i++) {
            sender.Sender(exchange, routingkey, msg+i);
        }
        return "发送消息";
    }
}
