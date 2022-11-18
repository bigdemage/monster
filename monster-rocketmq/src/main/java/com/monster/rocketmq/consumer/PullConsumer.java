package com.monster.rocketmq.consumer;


import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 拉取方式消费
 * 两种方式，DefaultMQPullConsumer不推荐使用、
 * 推荐使用DefaultLitePullConsumer，有LitePullConsumerAssign和LitePullConsumerSubscribe
 * 官方源码的example包中的simple
 */
public class PullConsumer {
    public static void main(String[] args) throws MQClientException {

        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer("lite_pull_consumer_test");
        litePullConsumer.setNamesrvAddr(NAME_SERVER_URL);
        //从第一个offset开始
        litePullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        litePullConsumer.subscribe("base_topic", "*");
        litePullConsumer.start();
        try {
            while (true) {
                List<MessageExt> messageExts = litePullConsumer.poll();
                if(CollectionUtils.isNotEmpty(messageExts)){
                    System.out.printf("%s%n", messageExts);
                }
            }
        } finally {
            litePullConsumer.shutdown();
        }
    }

}
