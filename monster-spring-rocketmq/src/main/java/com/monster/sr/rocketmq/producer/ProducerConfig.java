package com.monster.sr.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  RokcetMQ 配置
 */
@Configuration
@Slf4j
public class ProducerConfig {

    // 生产者组
    private final static String ORDER_PRODUCER_GROUP = "orderProducerGroup";
    //机器的cpu个数
    private static final int cpuCount=Runtime.getRuntime().availableProcessors();

    private final static Integer KEEP_ALIVE_TIME = 20;

    private final static Integer SEND_TIME_OUT= 60*1000;

    @Value("${rocketmq.nameserver}")
    String nameServer;


    // 执行事务任务的线程池
    private static ThreadPoolExecutor TRANSACTION_EXECUTOR =
            new ThreadPoolExecutor(
                    cpuCount+1,
                    cpuCount+2,
                    KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(100));

    @Autowired
    private TransactionListener transactionListener;

    @Bean(value = "transactionMQProducer")
    public TransactionMQProducer createTransactionProducer() throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer(ORDER_PRODUCER_GROUP);
        producer.setNamesrvAddr(nameServer);
        //单位毫秒
        producer.setSendMsgTimeout(SEND_TIME_OUT);
        producer.setExecutorService(TRANSACTION_EXECUTOR);
        producer.setTransactionListener(transactionListener);
        producer.start();
        log.info("生产者启动成功,nameServer={}",nameServer);
        return producer;
    }


}
