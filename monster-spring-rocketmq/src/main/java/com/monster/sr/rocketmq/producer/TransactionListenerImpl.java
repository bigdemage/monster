package com.monster.sr.rocketmq.producer;

import com.alibaba.fastjson.JSON;
import com.monster.sr.entity.OrderPO;
import com.monster.sr.entity.TransactionLogPO;
import com.monster.sr.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 事务监听器
 */
@Slf4j
@Component
public class TransactionListenerImpl implements TransactionListener {

    @Autowired
    private OrderService orderService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String transactionId = msg.getTransactionId();
        log.info("开始执行本地事务，事务编号:{}",transactionId);
        try {
            String orderPOJSON = new String(msg.getBody(), "UTF-8");
            OrderPO orderPO = JSON.parseObject(orderPOJSON, OrderPO.class);
            orderService.updateOrder(orderPO.getId(), transactionId);
            log.info("结束执行本地事务，事务编号：{}",transactionId);
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            log.error("本地事务执行异常，事务编号：{},{}",transactionId,e);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    /**
     * 检查本地事务执行状态，如果executeLocalTransaction方法中返回的状态是未知UNKNOWN或者未返回状态，
     * 默认会在预处理发送的1分钟后由Broker通知Producer检查本地事务，在Producer中回调本地事务监听器中的
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info("{}开始检测本地事务", LocalDateTime.now());
        String transactionId = msg.getTransactionId();
        LocalTransactionState localTransactionState;
        try {
            log.info("检测本地事务，事务编号：" + transactionId);
            TransactionLogPO transactionLogPO = orderService.getLogById(transactionId);
            if (transactionLogPO != null) {
                localTransactionState = LocalTransactionState.COMMIT_MESSAGE;
            } else {
                localTransactionState = LocalTransactionState.UNKNOW;
            }
        } catch (Exception e) {
            log.error("checkLocalTransaction error:", e);
            localTransactionState = LocalTransactionState.UNKNOW;
        }
        log.info("检测本地事务，事务编号：" + transactionId + " 事务状态：" + localTransactionState);
        return localTransactionState;
    }

}
