package com.monster.rocketmq.filter;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 使用sql过滤
 */
public class SqlProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer=new DefaultMQProducer("my-producer1");
        //连接nameServer
        producer.setNamesrvAddr(NAME_SERVER_URL);

        producer.setSendMsgTimeout(100000);

        String[] tags={"tagA","tagB","tagC"};

        producer.start();

        for(int i=1;i<=10;i++){
            String tag=tags[i%tags.length];
            String body="hello bolo"+i;
            Message message=new Message("sql_topic",tag,body.getBytes(StandardCharsets.UTF_8));

            //增加键值对--每条消息都有个键值对
            //要使用这个玩意，一定要在broker配置上增加enablePropertyFilter=true
            message.putUserProperty("a",i+"");

            SendResult result=producer.send(message);
            System.out.println("发送消息成功--"+message);

        }

        producer.shutdown();


    }

}
