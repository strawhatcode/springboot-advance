package com.hat.rabbitmq.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration  //把该类定义为配置类
public class rabbitmqConfiguration {
    private static final Logger log = LoggerFactory.getLogger(rabbitmqConfiguration.class);

    /**
     * 无交换器的消息队列
     * 参数：
     *     name：队列名称，如果使用无交换器的消息队列则同时充当路由键(routing key)
     *     durable：queue持久化，即使消息代理崩溃，队列也不会消失，默认true
     *     exclusive：排他性，如果为true，那么除了当前连接可以使用该队列外，其他连接都不能使用该队列，默认false
     *     autoDelete：自动删除，在最后一个连接断开时，queue会自动删除，默认false
     * @return
     */
    @Bean //注册到spring容器
    public Queue queue(){
        //参数是队列名称，不使用交换器时队列名称相当于路由键，
        //接收者需要根据队列名称来接收消息
        return new Queue("defaultQueue",true,false,false);
    }



    /**
     * direct exchange交换机(直连交换器)的消息队列
     * rabbitmq默认使用的交换机就是直连交换器。
     * @return
     */
    @Bean
    public Queue directQueue_A(){
        return new Queue("directQueueA");
    }
    @Bean
    public Queue directQueue_B(){
        return new Queue("directQueueB");
    }
    /**
     * 创建direct Exchange交换机
     *      参数：
     *          name：交换机的名字
     *          durable：是否持久化， 默认true
     *          autoDelete：是否自动删除， 默认false
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange",true, false);
    }

    /**
     * 对消息队列和交换机进行绑定
     *      bind()：要绑定的队列
     *      to()：绑定的交换机
     *      with()：路由键
     * @return
    */
    @Bean
    public Binding bindDirectExchange_A(){
        return BindingBuilder.bind(this.directQueue_A()).to(this.directExchange()).with("directA");
    }
    @Bean
    public Binding bindDirectExchange_B(){
        return BindingBuilder.bind(this.directQueue_B()).to(this.directExchange()).with("directB");
    }
    @Bean
    public Binding bindDirectExchange_C(){
        return BindingBuilder.bind(this.directQueue_A()).to(this.directExchange()).with("directB");
    }



    /**
     *  fanout Exchange交换机
     *  任何发送到[fanout交换机]的消息都会转发到绑定了该交换机的队列
     *  这种交换机不需要路由键，只要与fanout交换机绑定的队列都可以收到消息
     *  通俗来讲就是一个[发布者]发布的消息可以被多个[订阅者]接收
     * @return
     */
    @Bean
    public Queue fanoutQueue_A(){
        return new Queue("fanoutQueueA");
    }
    @Bean
    public Queue fanoutQueue_B(){
        return new Queue("fanoutQueueB");
    }
    @Bean
    public Queue fanoutQueue_C(){
        return new Queue("fanoutQueueC");
    }

    /**
     * 创建fanout Exchange交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 把消息队列与fanout交换机绑定起来，路由键可以不用设置，即使设置了也无任何变化
     * @return
     */
    @Bean
    public Binding bindFanoutExchange_A(){
        return BindingBuilder.bind(fanoutQueue_A()).to(fanoutExchange());
    }
    @Bean
    public Binding bindFanoutExchange_B(){
        return BindingBuilder.bind(fanoutQueue_B()).to(fanoutExchange());
    }
    @Bean
    public Binding bindFanoutExchange_C(){
        return BindingBuilder.bind(fanoutQueue_C()).to(fanoutExchange());
    }



    /**
     * topic Exchange交换机(主题交换机)的队列
     * topic交换机可以根据通配符作为匹配条件来匹配路由键，它提供了两个通配符(*)和(#)。
     *      * ：代表一个单词，如当路由键为 aa.bb.cc,则 aa.* 无法匹配到此路由键，aa.bb.* 或者 *.bb.cc 或者 aa.*.cc则可以匹配
     *      # ：代表任意个单词，如当路由键为 aa.bb.cc，则aa.#可以匹配，它可以匹配后面任意个单词(0个也可以)
     * @return
     */
    @Bean
    public Queue topicQueue_A(){
        return new Queue("topicQueueA");
    }
    @Bean
    public Queue topicQueue_B(){
        return new Queue("topicQueueB");
    }

    /**
     * 创建topic交换机
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }
    @Bean
    public TopicExchange topicExchangeB(){
        return new TopicExchange("topicExchangeB");
    }

    /**
     * 绑定消息队列与交换机
     *  这里3个绑定是用来测试 （*）通配符的
     * @return
     */
    @Bean
    public Binding bindTopicExchange_A1(){
        return BindingBuilder.bind(topicQueue_A()).to(topicExchange()).with("topic.aa.*");
    }
    @Bean
    public Binding bindTopicExchange_A2(){
        return BindingBuilder.bind(topicQueue_A()).to(topicExchange()).with("*.topic.aa");
    }
    @Bean
    public Binding bindTopicExchange_A3(){
        return BindingBuilder.bind(topicQueue_A()).to(topicExchange()).with("topic.*.bb");
    }

    /**
     * 这里3个绑定是用来测试 （#）通配符的
     * @return
     */
    @Bean
    public Binding bindTopicExchange_B1(){
        return BindingBuilder.bind(topicQueue_B()).to(topicExchangeB()).with("topic.aa.#");
    }
    @Bean
    public Binding bindTopicExchange_B2(){
        return BindingBuilder.bind(topicQueue_B()).to(topicExchangeB()).with("#.topic.aa");
    }
    @Bean
    public Binding bindTopicExchange_B3(){
        return BindingBuilder.bind(topicQueue_B()).to(topicExchangeB()).with("topic.#.bb");
    }



    /**
     *  headers Exchange(头部交换机)
     *  这种交换机[不需要]路由键，但是需要一个headers消息体作为匹配条件，与http的headers相似，
     *  它与routingKey的区别是可以匹配object类型的属性，而routingKey只能匹配string类型的属性
     * @return
     */
    @Bean
    public Queue headerQueueA(){
        return new Queue("headerQueueA");
    }
    @Bean
    public Queue headerQueueB(){
        return new Queue("headerQueueB");
    }

    /**
     * 创建headers Exchange交换机
     * @return
     */
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange("headersExchange");
    }

    /**
     * 绑定headers Exchange和消息队列。
     *      whereAll：全属性匹配，还有[whereAny],是部分匹配。 这里参数是一个map对象(headers)
     *      match：就是匹配
     * @return
     */
    @Bean
    public Binding bindHeadersExchangeAll(){
        Map<String, Object> headers = new HashMap<>();
        headers.put("key1","value1");
        headers.put("key2","value2");
        return BindingBuilder.bind(headerQueueA()).to(headersExchange()).whereAll(headers).match();
    }

    @Bean
    public Binding bindHeadersExchangeAny(){
        Map<String, Object> headers = new HashMap<>();
        headers.put("key1","value1");
        headers.put("key2","value2");
        return BindingBuilder.bind(headerQueueB()).to(headersExchange()).whereAny(headers).match();
    }


    /**
     * 创建死信队列，这里使用topicExchange交换机
     * @return
     */
    @Bean
    public Queue deadQueue(){
        return new Queue("dead.queue");
    }
    @Bean
    public TopicExchange deadTopicExchange(){
        return new TopicExchange("deadExchange");
    }
    @Bean
    public Binding bindDeadTopicExchange(){
        return BindingBuilder.bind(deadQueue()).to(deadTopicExchange()).with("dead.key.#");
    }

    /**
     * 把一个direct交换机的队列绑定死信队列，那么这个队列的消息出现死信现象就会把消息发送到死信队列【dead.queue】中
     * 把normalQueue队列设置参数绑定死信队列
     *      x-dead-letter-exchange：死信交换机
     *      x-dead-letter-routing-key：死信路由键
     *      x-message-ttl：消息存活时间(这个设不设都可以)，单位毫秒
     * @return
     */
    @Bean
    public Queue normalQueue(){
        Map<String,Object> dead_args = new HashMap<>();
        dead_args.put("x-dead-letter-exchange","deadExchange"); //死信交换机，与上面死信交换机名称匹配
        dead_args.put("x-dead-letter-routing-key","dead.key.test"); //死信路由键，与上面死信交换机绑定时的路由键匹配
        dead_args.put("x-message-ttl",60000);  //消息存活时间，单位毫秒
        //第五个参数是绑定死信的一些参数，map类型
        return new Queue("normalQueue",true,false,false,dead_args);
    }
    @Bean
    public DirectExchange normalExchange(){
        return new DirectExchange("normalExchange");
    }
    @Bean
    public Binding bindNormalExchange(){
        return BindingBuilder.bind(normalQueue()).to(normalExchange()).with("normalkey");
    }


    //注入CachingConnectionFactory连接工厂，可以设置或者获取yml配置文件中的配置
    @Autowired
    private CachingConnectionFactory factory;

    /**
     * 设置消息发布者confirm机制
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbit = new RabbitTemplate(factory); //实例化rabbitTemplate模版连接
        /**
         * 消息发布到broker就会回调
         * 参数：
         *      correlationData：消息唯一id，确保每个消息都有一个唯一id
         *      ack：是否成功，true为可以到达交换机，false则不可以到达交换机
         *      cause：如果ack为false时，不能到达交换机的原因
         */
        rabbit.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack){
//                log.info("【到达Exchange成功】：correlationData({}),ack({}),cause({})",correlationData,ack,cause);
//                log.info("【到达Exchange成功】：correlationData({})",correlationData);
//                log.info("【到达Exchange成功】：ack({})",ack);
//                log.info("【到达Exchange成功】：cause({})",cause);
            }else {
                log.info("【到达Exchange失败】：correlationData({}),ack({}),cause({})",correlationData,ack,cause);
//                log.info("【到达Exchange失败】：correlationData({})",correlationData);
//                log.info("【到达Exchange失败】：ack({})",ack);
//                log.info("【到达Exchange失败】：cause({})",cause);
            }
        });

        /**
         * 当消息路由到队列失败时回调
         * 参数：
         *      message：消息内容
         *      replyCode：返回编码
         *      replyText：返回内容
         *      exchange： 交换机名称
         *      routingKey：路由键
         */
        rabbit.setMandatory(true);  //一定要设置setMandatory为True，不然失败时无法回调
        rabbit.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("【消息丢失】:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
                    exchange,routingKey,replyCode,replyText,message);
//            log.info("【消息丢失】:message({})",message);
//            log.info("【消息丢失】:replyCode({})",replyCode);
//            log.info("【消息丢失】:replyText({})",replyText);
//            log.info("【消息丢失】:exchange({})",exchange);
//            log.info("【消息丢失】:routingKey({})",routingKey);
        });

        return rabbit;
    }

}
