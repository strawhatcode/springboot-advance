package com.hat.rabbitmq.mqsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqSenderFanoutExchange {
    private static final Logger log = LoggerFactory.getLogger(MqSenderDirectExchange.class);

    //注入AmqpTemplate
    @Autowired
    AmqpTemplate rabbitTemplate;

    /**
     * 发布者，需要指定exchange(创建交换机的名称)，
     * 路由键设置为空就行，即使指定了路由键，fanout交换机也不会根据路由键查找相应队列，
     * 只要与fanout交换机绑定的队列都会收到消息
     */
    public void Sender(String msg){
        rabbitTemplate.convertAndSend("fanoutExchange","", msg);
        log.info("【fanoutExchange】发送了信息---"+msg);
    }
}
