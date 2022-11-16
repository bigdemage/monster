package com.monster.rocketmq.delay;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 延迟发送--延迟效果在消费者端
 */
public class DelayProducer {
    public static void main(String[] args) throws Exception {
        //创建生产者
        DefaultMQProducer producer=new DefaultMQProducer("my-producer");
        //连接nameServer
        producer.setNamesrvAddr(NAME_SERVER_URL);

        producer.setSendMsgTimeout(5000);
        //启动生产者
        producer.start();
        for(int i=1 ;i<=10;i++){
            //创建消息
            Message message=new Message("delay_topic","tagA",("hello mother"+i).getBytes(StandardCharsets.UTF_8));

            //设置延迟等级--不同等级对应不同的时间，在控制台broker的配置中可以看到messageDelayLevel有18个不同的等级，从1开始
            //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            //查看系统的topic，SCHEDULE_TOPIC_XXXX由它来实现
            message.setDelayTimeLevel(3);

            //发送消息
            SendResult result=producer.send(message);
            System.out.println("结果:"+result);
        }
        //关闭生产者
        producer.shutdown();
    }
}
