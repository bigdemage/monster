package com.monster.rocketmq.simpleMsg;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 单向发送消息，不需要关注结果
 */
public class OnewayProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer=new DefaultMQProducer("simple_msg");
        producer.setNamesrvAddr(NAME_SERVER_URL);
        //设个超时时间，太短了容易sendDefaultImpl call timeout
        producer.setSendMsgTimeout(5000);
        producer.start();

        for(int i=1;i<=5;i++){
            Message message=new Message("simple_msg","tagA",("one way msg"+i).getBytes(StandardCharsets.UTF_8));
            producer.sendOneway(message);
        }

        producer.shutdown();
    }
}
