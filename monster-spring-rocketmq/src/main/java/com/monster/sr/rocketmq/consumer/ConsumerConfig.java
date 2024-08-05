package com.monster.sr.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.monster.sr.common.Constant.ORDER_TOPIC;

@Slf4j
@Component
public class ConsumerConfig {

    //消费者组
    private final static String ORDER_POINT_CONSUMER_GROUP = "orderPointConsumerGroup";

    private final static String PRODUCT_CONSUMER_GROUP = "productConsumerGroup";

    // 事务消费者
    @Autowired
    private OrderPointMessageListener orderPointMessageListener;

    @Value("${rocketmq.nameserver}")
    private String nameServer;

    // 商品异构到 Elasticsearch
//    @Autowired
//    private ProductToESMessageListener productToESMessageListener;

    @Bean
    public DefaultMQPushConsumer createTransactionConsumer() throws MQClientException {
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(ORDER_POINT_CONSUMER_GROUP);
        pushConsumer.setNamesrvAddr(nameServer);
        pushConsumer.setConsumeMessageBatchMaxSize(1);
        pushConsumer.subscribe(ORDER_TOPIC, "*");
        pushConsumer.registerMessageListener(orderPointMessageListener);
        pushConsumer.start();
        log.info("消费者启动成功，nameServer:{},topic:{},consumerGroup:{}", nameServer, ORDER_TOPIC, ORDER_POINT_CONSUMER_GROUP);
        return pushConsumer;
    }

//    @Bean
//    public DefaultMQPushConsumer createProductConsumer() throws MQClientException {
//        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(PRODUCT_CONSUMER_GROUP);
//        pushConsumer.setNamesrvAddr("192.168.182.129:9876");
//        pushConsumer.setConsumeMessageBatchMaxSize(1);
//        pushConsumer.subscribe("product-syn-topic", "*");
//        pushConsumer.registerMessageListener(productToESMessageListener);
//        pushConsumer.start();
//        return pushConsumer;
//    }

}
