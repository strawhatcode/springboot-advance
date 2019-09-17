package com.hat.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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



}
