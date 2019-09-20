package com.hat.rabbitmq.controller;

import com.hat.rabbitmq.mqsender.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderController {
    @Autowired
    MqSender sender;

    @GetMapping("/send")
    public String send(String exchange,
                       String queue,
                       String msg){
        for(int i=1; i<10;i++) {
            sender.Sender(exchange, queue, msg+i);
        }
        return "发送消息";
    }
}
