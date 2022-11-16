package com.monster.kafka;

import com.mq.kafka.core.conf.ConsumerConfig;
import com.mq.kafka.core.handle.ReceiveCore;
import com.mq.kafka.core.parse.AbstractMessageParse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.monster.kafka.vo.SyncConstant.Topic.*;

/**
 * @ClassName YztConsumer
 * @Deacription TODO
 * @Author wrx
 * @Date 2022/3/4/004 13:43
 * @Version 1.0
 **/
@Slf4j
public class YztConsumer {

    private static String[] topics = {
            syncAlterCustomerInfo,
            syncAlterClientInfo,
            syncModifyPassword,
            syncResetPassword,
            syncAlterClientBaseInfo,
            syncAlterTaxResidentStatus,
            syncInsertTaxResidentStatus,
            syncUpdateAuditInfoPersonal,
            syncSaveAudit
    };

    private static final String group="sawa_dika";

    public static void main(String[] args) {

        ExecutorService service= Executors.newFixedThreadPool(10);

        for (String topic : topics) {
            ConsumerConfig consumerConfig = new ConsumerConfig();
            consumerConfig.setTopic(topic);
            consumerConfig.setBootstrapServers(SomeConfig.A_KAFKA_URL);
            consumerConfig.setGroupId(group);
            ReceiveCore receiveCore = new ReceiveCore(
                    consumerConfig, new AbstractMessageParse() {
                @Override
                public int parseMessage(String message) {
                    return 0;
                }
            });
            service.execute(receiveCore);
//            new Thread(receiveCore,topic).start();
        }
        System.out.println("线程启动，开始监听");

    }

}
