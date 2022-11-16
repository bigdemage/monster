package com.monster.rocketmq.simpleMsg;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 同步消息发送
 * 同步发送一定要捕获异常，方便业务失败后的处理
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer=new DefaultMQProducer("simple_msg_sync");
        producer.setNamesrvAddr(NAME_SERVER_URL);
        //设个超时时间，太短了容易sendDefaultImpl call timeout
        producer.setSendMsgTimeout(5000);
        producer.start();

        for(int i=1;i<=5;i++){
            Message message=new Message("simple_msg","tagA",("ni hao"+i).getBytes(StandardCharsets.UTF_8));
            //同步等待发送结果
            SendResult result=producer.send(message);
            System.out.printf("%s%n",result);
        }

        producer.shutdown();


    }
}
