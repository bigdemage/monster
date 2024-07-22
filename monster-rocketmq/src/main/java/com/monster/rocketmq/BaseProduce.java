package com.monster.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

public class BaseProduce {

    public static void main(String[] args) throws Exception {
        //创建生产者
        DefaultMQProducer producer=new DefaultMQProducer("my-producer1");
        //连接nameServer
        producer.setNamesrvAddr(NAME_SERVER_URL);

        producer.setSendMsgTimeout(60000);
        //启动生产者
        producer.start();
        for(int i=1 ;i<=10;i++){
            //创建消息
            Message message=new Message("TestTopic","tagA",("hello mother"+i).getBytes(StandardCharsets.UTF_8));
            //发送消息
            SendResult result=producer.send(message);
            System.out.println("结果:"+result);
        }
        //关闭生产者
        producer.shutdown();
    }
}
