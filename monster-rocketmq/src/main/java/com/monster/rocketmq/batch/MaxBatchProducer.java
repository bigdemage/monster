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
 * 一个批次官方说1M，其实是4M
 * MQClientException: CODE: 13  DESC: the message body size over max value, MAX: 4194304
 * 当批次大了之后，通过官方分割工具对批次进行分割
 *
 * 如果报错DESC: the message is illegal, maybe msg body or properties length not matched. msg body length limit 65536B
 * 说明broker配置maxMessageSize最大值不够list总大小，需要重新配置
 */
public class MaxBatchProducer {

    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {

        //创建生产者
        DefaultMQProducer producer=new DefaultMQProducer("my-producer1");
        //连接nameServer
        producer.setNamesrvAddr(NAME_SERVER_URL);

        producer.setSendMsgTimeout(5000);
        //启动生产者
        producer.start();

        List<Message> list=new ArrayList<>(10*1000);

        for(int i=1 ;i<=10*1000;i++){
            //创建消息
            Message message=new Message("batch_topic_splitter","tagA",("hello mother"+i).getBytes(StandardCharsets.UTF_8));
            list.add(message);
        }

        ListSplitter splitter=new ListSplitter(list);
        while(splitter.hasNext()){
            List<Message> list2=splitter.next();
            SendResult result=producer.send(list2);
            System.out.println(result);
        }
        //关闭生产者
        producer.shutdown();
    }

}
