package com.monster.rocketmq.transaction;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 事务消息
 */
@Slf4j
public class TransactionProducer {

    public static void main(String[] args) throws Exception{

        //定义事务处理逻辑
        TransactionListener transactionListener=new TransactionListenerImpl();
        //事务消息生产者
        TransactionMQProducer producer=new TransactionMQProducer("my-producer");
        producer.setSendMsgTimeout(100000);
        producer.setNamesrvAddr(NAME_SERVER_URL);
        //自定义线程池
        ExecutorService executor=new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread=new Thread(r);
                thread.setName("transaction_mq_thread");
                return thread;
            }
        });

        producer.setExecutorService(executor);
        producer.setTransactionListener(transactionListener);
        producer.start();

        String[] tags={"Ta","Tb","Tc","Td","Te"};

        for(int i=1;i<=10;i++){
            String tag=tags[i%tags.length];
            Message msg =new Message("transaction_topic",tag,("hi kiki"+i).getBytes(StandardCharsets.UTF_8));
            //使用事务发送
            TransactionSendResult result=producer.sendMessageInTransaction(msg,"token");
            log.info("发送信息:{}",result);
        }

//        producer.shutdown();







    }
}
