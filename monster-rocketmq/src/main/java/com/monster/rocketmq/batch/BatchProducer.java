package com.monster.rocketmq.batch;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 批量发送，减少io
 * 官方建议批量一次不超过1M，实际是不超过4M
 */
public class BatchProducer {

    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        //创建生产者
        DefaultMQProducer producer=new DefaultMQProducer("my-producer1");
        //连接nameServer
        producer.setNamesrvAddr(NAME_SERVER_URL);

        producer.setSendMsgTimeout(5000);
        //启动生产者
        producer.start();

        List<Message> list=new ArrayList<>();

        for(int i=1 ;i<=10;i++){
            //创建消息
            Message message=new Message("batch_topic","tagA",("hello mother"+i).getBytes(StandardCharsets.UTF_8));
            list.add(message);
        }
        SendResult result=producer.send(list);

        System.out.println(result);

        //关闭生产者
        producer.shutdown();
    }
}
