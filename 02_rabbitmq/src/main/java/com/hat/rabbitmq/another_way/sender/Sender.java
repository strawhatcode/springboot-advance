package com.hat.rabbitmq.another_way.sender;


import com.hat.rabbitmq.another_way.utils.ConnectionUtil;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//把该类注入到spring容器，方便测试
@Component
public class Sender {
    private final static Logger log = LoggerFactory.getLogger(Sender.class);
    //声明队列名称、交换机名称和路由键
    private String QUEUE_NAME = "another.directQueue";
    private String EXCHANGE_NAME = "another.directExchange";
    private String ROUTING_KEY = "another.direct.routingkey";

    /**
     *  direct交换机的消息发布者
     * @param msg
     * @throws IOException
     * @throws TimeoutException
     */
    public void directSender(String aexchange,String routingkey,String msg) throws IOException, TimeoutException {
        //创建一个连接
        Connection conn = ConnectionUtil.connectionFactory();
        //创建一个通道
        Channel channel = conn.createChannel();
        //创建一个队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //创建一个direct交换机，如果不使用交换机则不用创建
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true);
        //队列与交换机根据路由键绑定起来
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        //开启confirm机制
        channel.confirmSelect();
        //消费者每次从队列中接收多少个消息，与yml配置的【prefetch】一样
        channel.basicQos(1);
        //发布消息
        channel.basicPublish(aexchange,routingkey,true,null,msg.getBytes());
        //监听发布者消息是否发布成功
        channel.addConfirmListener(new ConfirmListener() {
            //消息发送成功回调
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                //deliveryTag：消息标识。multiple：是否处理了之前未发送成功的消息
                log.info("[directSender]的【handleAck】deliveryTag==[{}],multiple==[ {} ]",deliveryTag,multiple);
            }
            //消息发布失败回调
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                log.info("[directSender]的【handleNack】deliveryTag==[{}],multiple==[ {} ]",deliveryTag,multiple);
            }
        });
        //消息入队列失败回调，成功不回调，与rabbitmqTemplate.returnCallback一样
        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) ->
                log.info("[directSender]的【handleReturn】replyCode==[{}],replyText==[ {} ]," +
                "routingKey==[{}],properties==[{}],body==[{}]",replyCode,replyText,exchange,routingKey,properties,body));
        log.info("[directSender]发送消息--[{}]",msg);
        //关闭通道
        channel.close();
        //关闭连接
        conn.close();
    }

}
