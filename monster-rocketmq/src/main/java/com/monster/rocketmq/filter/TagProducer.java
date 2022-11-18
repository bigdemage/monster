package com.monster.rocketmq.filter;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 过滤消息,带tag
 */
public class TagProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer=new DefaultMQProducer("my-producer1");
        //连接nameServer
        producer.setNamesrvAddr(NAME_SERVER_URL);

        producer.setSendMsgTimeout(5000);

        String[] tags={"tagA","tagB","tagC"};

        producer.start();

        for(int i=1;i<=10;i++){
            String tag=tags[i%tags.length];
            String body="hello bolo"+i;
            Message message=new Message("tag_topic",tag,body.getBytes(StandardCharsets.UTF_8));
            producer.sendOneway(message);
        }

        producer.shutdown();

    }
}
