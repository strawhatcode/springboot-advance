package com.hat.rabbitmq;

import com.hat.rabbitmq.mqreceiver.MqReceiverNoExchange;
import com.hat.rabbitmq.mqsender.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    MqSenderNoExchange mqSender;
    @Autowired
    MqReceiverNoExchange mqReceiver;

    @Autowired
    MqSenderDirectExchange directSender;

    @Test
    public void textdefault() {
        mqSender.defaultQueueSender("aaaaaaaaaaaaaaa");
        mqSender.defaultQueueSender("bbbbbbbbbbbbb");
        mqSender.defaultQueueSender("ccccccccc" );
        mqSender.defaultQueueSender("dddddddddddd" );
        mqSender.defaultQueueSender("eeeeeeeeeeee" );
        mqSender.defaultQueueSender("fffffffffff" );
        mqSender.defaultQueueSender("gggggggggg" );
    }

    @Test
    public void testdirect(){
        directSender.SenderA("【SenderA】发送的信息");
        directSender.SenderA("【SenderA】发送的第二条信息");
        directSender.SenderA("【SenderA】发送的第三条信息");

        directSender.SenderB("【SenderB】发送的信息");
        directSender.SenderB("【SenderB】发送的第二条信息");
        directSender.SenderB("【SenderB】发送的第三条信息");
    }

    @Autowired
    MqSenderFanoutExchange fanoutSender;
    @Test
    public void testfanout(){
        fanoutSender.Sender("【fanout】发送的第一条信息");
        fanoutSender.Sender("【fanout】发送的第二条信息");
    }

    @Autowired
    MqSenderTopicExchange topicSender;
    @Test
    public void testtopic(){
        topicSender.SenderA("发送的路由键为[topic.aa.test1]--1", "topic.aa.test1");
        topicSender.SenderA("发送的路由键为[topic.aa.bb.test1.tt]--2", "topic.aa.test1.tt");
        topicSender.SenderA("发送的路由键为[topic.bb.aa]--3", "topic.bb.aa");
        topicSender.SenderA("发送的路由键为[topic.test1.bb]--4", "topic.test1.bb");
        topicSender.SenderA("发送的路由键为[topic.aa]--5", "topic.aa");
        topicSender.SenderA("发送的路由键为[test1.topic.aa]--6", "test1.topic.aa");
        topicSender.SenderA("发送的路由键为[tt.test1.topic.aa]--7", "tt.test1.topic.aa");
    }


    @Test
    public void testtopicB(){
        topicSender.SenderB("发送的路由键为[topic.aa.test1]--1", "topic.aa.test1");
        topicSender.SenderB("发送的路由键为[topic.aa.test1.test2]--2", "topic.aa.test1.test2");
        topicSender.SenderB("发送的路由键为[topic.aa]--3", "topic.aa");
        topicSender.SenderB("发送的路由键为[test1.topic.aa]--4", "test1.topic.aa");
        topicSender.SenderB("发送的路由键为[test2.test1.topic.aa]--5", "test2.test1.topic.aa");
        topicSender.SenderB("发送的路由键为[topic.bb.aa]--6", "topic.bb.aa");
        topicSender.SenderB("发送的路由键为[topic.aa.bb]--7", "topic.aa.bb");
        topicSender.SenderB("发送的路由键为[test2.topic.aa.test1.bb]--8", "test2.topic.aa.test1.bb");
        topicSender.SenderB("发送的路由键为[topic.aa.test1.bb.test2]--9", "topic.aa.test1.bb.test2");
        topicSender.SenderB("发送的路由键为[test2.topic.aa.test1]--10", "test2.topic.aa.test1");
        topicSender.SenderB("发送的路由键为[topic.bb]--11", "topic.bb");
    }

    @Autowired
    MqSenderHeadersExchange headerSender;

    @Test
    public void testheader(){

        MessageProperties properties1 = new MessageProperties();
        properties1.setHeader("key2","value2");

        MessageProperties properties2 = new MessageProperties();
        properties2.setHeader("key1","value1");
        properties2.setHeader("key2","value2");

        MessageProperties properties3 = new MessageProperties();
        properties3.setHeader("key2","value2");
        properties3.setHeader("key1","value1");
        properties3.setHeader("key3","value3");

        headerSender.Sender("1--发送的header为[k2,v2]",properties1);
        headerSender.Sender("2--发送的header为[k1,v1、k2,v2]",properties2);
        headerSender.Sender("3--发送的header为[k1,v1、k2,v2、k3,v3]",properties3);
    }

}
