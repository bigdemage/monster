package com.monster.kafka;

import com.mq.kafka.core.conf.ConsumerConfig;
import com.mq.kafka.core.handle.ReceiveCore;
import com.mq.kafka.core.parse.AbstractMessageParse;

/**
 * @ClassName JieShou
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/11/10/010 16:27
 * @Version 1.0
 **/
public class JieShou {
    public static void main(String[] args) {


        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTopic("sms_send_wrx");
        consumerConfig.setBootstrapServers(SomeConfig.D_KAFKA_URL);
        consumerConfig.setGroupId("sawa_dika");
        ReceiveCore receiveCore = new ReceiveCore(
                consumerConfig, new AbstractMessageParse() {
            @Override
            public int parseMessage(String message) {
                System.out.println(message);
                return 0;
            }
        });
        new Thread(receiveCore).start();
    }

}
