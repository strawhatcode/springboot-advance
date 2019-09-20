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
public class MqReceiverDirectExchange {
    private final static Logger log = LoggerFactory.getLogger(MqReceiverDirectExchange.class);
    int count = 0; //记录重试的次数
    @RabbitListener(queues = "directQueueA")
    @RabbitHandler
    public void ReceiverA(Message msg, Channel channel) throws IOException {
        String getQueueName = msg.getMessageProperties().getConsumerQueue();
        String massage = new String(msg.getBody());
//        log.info(msg.getMessageProperties().toString());
        //模拟如果出现异常或者出现其他问题时导致消息处理失败时的处理
        if ("这是消息5".equals(massage) || "这是消息7".equals(massage) ){
            log.info("*************************************************");
            count += 1;
            if(count < 6) {
                log.info("【消息[{}]重试第[{}]次】",massage,count);
                /**
                 * basicNack()：消息失败确认，然后做相应的操作(重新发送或者丢弃)
                 * 模拟如果出现异常或者出现其他问题，导致消息处理失败时，把消息重新发送5次。
                 * 如果不自己处理次数则会无限循环。
                 *  参数：
                 *      deliveryTag：标识消息，第一个为1，然后依次加1
                 *      multiple：
                 *          True：把当前标识(deliveryTag)之前的未确认消息全部确认，
                 *                如当前deliveryTag为7时，前面还有4、5、6，则把4、5、6、7标识的消息都确认
                 *          False：把当前标识(deliveryTag)的消息进行确认，
                 *                 如当前为7，则只确认7，如果还有4、5、6,并不会对他们进行确认
                 *      requeue：True时将消息重新入队列，如果不加判断条件会出现死循环，False则丢弃该消息
                 */
                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
            }else {
                log.info("【重试次数已超过，丢弃消息[{}]】",massage);
                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
                count = 0;
            }
        }else {
            /**
             * basicAck()：消息成功确认，参数与basicNack()一样，就少了个requeue参数
             * 处理完消息后就把deliveryTag返回给broker，然后broker会删除对应消息
             */
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), true);
            log.info("【ReceiverA】接收到来自队列[ " + getQueueName + " ]的信息---->[ " + massage + " ]");
        }
//        try {
//            String getQueueName = msg.getMessageProperties().getConsumerQueue();
//            String massage = new String(msg.getBody());
//            int a = 1 / 0;
//            channel.basicAck(msg.getMessageProperties().getDeliveryTag(),true);
//            log.info("【ReceiverA】接收到来自队列[ "+getQueueName+" ]的信息---->[ "+massage+" ]");
//        } catch (IOException e) {
//            e.printStackTrace();
//            count += 1;
//            if(count < 5) {
//                log.info("【重试第[{}]次】",count);
//                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
//            }else {
//                log.info("【重试次数已超过，丢弃消息】");
//                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
//                count = 0;
//            }
//        }

    }

//    @RabbitListener(queues = {"directQueueB","directQueueA"})
    @RabbitListener(queues = {"directQueueB"})
    @RabbitHandler
    public void ReceiverB(Message msg, Channel channel) throws IOException { //接收Message类型的返回值(包括消息队列的一些信息)
        //获取当前消息是从哪个队列中获取的
        String getQueueName = msg.getMessageProperties().getConsumerQueue();
        //获取当前消费的消息内容，获取到的是byte类型，要转成String
        String massage = new String(msg.getBody());
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(),true);

        log.info("【ReceiverB】接收到来自队列[ "+getQueueName+" ]的信息---->["+massage+"]");
    }
}
