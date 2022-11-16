package com.monster.kafka;

import com.mq.kafka.core.conf.ProducerConfig;
import com.mq.kafka.core.handle.SendCore;

import java.util.UUID;

/**
 * @ClassName FaSong
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/11/10/010 16:27
 * @Version 1.0
 **/
public class FaSong {

    public static void main(String[] args) {
        ProducerConfig producerConfig = new ProducerConfig();
        producerConfig.setBootstrapServers(SomeConfig.D_KAFKA_URL);
        producerConfig.setAcks("1");
        int i=0;
        while(i<10){

            SendCore.send(producerConfig, "sms_send_wrx", "按住吧"+ UUID.randomUUID());
            ++i;
        }

    }
}
