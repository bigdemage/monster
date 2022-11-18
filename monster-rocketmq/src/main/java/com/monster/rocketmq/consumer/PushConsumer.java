package com.monster.rocketmq.consumer;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 推送方式消费
 *
 * broker推送给consumer
 */
public class PushConsumer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //创建生产者
        DefaultMQProducer producer=new DefaultMQProducer("my-producer1");
        //连接nameServer
        producer.setNamesrvAddr(NAME_SERVER_URL);

        producer.setSendMsgTimeout(5000);
        //启动生产者
        producer.start();
        for(int i=1 ;i<=10;i++){
            //创建消息
            Message message=new Message("transaction_topic","tagA",("hello mother"+i).getBytes(StandardCharsets.UTF_8));
            //发送消息
            SendResult result=producer.send(message);
            System.out.println("结果:"+result);
        }
        //关闭生产者
        producer.shutdown();
    }
}
