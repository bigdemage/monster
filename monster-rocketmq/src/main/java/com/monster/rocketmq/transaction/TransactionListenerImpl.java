package com.monster.rocketmq.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 本地事务处理逻辑
 */
@Slf4j
public class TransactionListenerImpl implements TransactionListener {

    private volatile AtomicInteger count=new AtomicInteger(0);

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String tag=msg.getTags();
        if("Ta".equals(tag)){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else if("Tb".equals(tag)){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }else{
            return LocalTransactionState.UNKNOW;
        }
    }

    /**
     * 回查一次事务，用于unknow后再次检查事务
     * @param msg Check message
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info("{}开始检测本地事务", LocalDateTime.now());
//        log.info("check次数：{}",count.getAndIncrement());
        log.info("msg:{}",new String(msg.getBody()));
        if("Td".equals(msg.getTags())){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else{
            return LocalTransactionState.UNKNOW;
        }
    }
}
