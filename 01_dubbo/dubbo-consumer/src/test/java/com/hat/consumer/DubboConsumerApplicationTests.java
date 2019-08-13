package com.hat.consumer;

import com.hat.consumer.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboConsumerApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(DubboConsumerApplicationTests.class);

    @Autowired
    UserController userController;
    private int COUNT = 0;

    /**
     * 测试(默认)随机负载均衡(random LoadBalance)
     */
    @Test
    public void contextLoads() {
        for (int i=1;i<=5;i++) {
            String str = userController.getUserApi();
            log.info("调用第"+i+"次，调用的服务--->"+str);
        }
    }

    /**
     * 测试轮询负载均衡（roundrobin LoadBalance）
     */
    @Test
    public void testRoundRobin(){
        for (int i=1;i<=10;i++) {
            String str = userController.getUserApi();
            log.info("调用第"+i+"次，调用的服务--->"+str);
        }
    }

    /**
     * 测试最少活跃调用数负载均衡（LeastActive LoadBalance）
     */
    @Test
    public void testLeastActive(){
        for (int i=1;i<=50;i++) {
            String str = userController.getUserApi();
            log.info("调用第"+i+"次，调用的服务--->"+str);
        }
    }

    /**
     * 测试一致性hash负载均衡（ConsistentHash  LoadBalance）
     * 每2秒调用一次
     */
    @Test
    public void testConsistentHash(){
        long lasttime = System.currentTimeMillis();
        while (COUNT<50) {
            if (2000 <= System.currentTimeMillis() - lasttime) {
                COUNT++;
                String str = userController.getUserApi();
                log.info("调用第" + COUNT + "次，调用的服务--->" + str);
                lasttime = System.currentTimeMillis();
            }
        }
    }
}
