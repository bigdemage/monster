package com.monster.sr.service;

import com.monster.sr.common.SnowFlakeIdGenerator;
import com.monster.sr.entity.OrderPO;
import com.monster.sr.entity.TransactionLogPO;
import com.monster.sr.mapper.OrderMapper;
import com.monster.sr.mapper.TransactionLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    public Long insertOrder(Long userId) {
        Long id = SnowFlakeIdGenerator.getUniqueId(userId.intValue(), 0);
        OrderPO orderPO = new OrderPO();
        orderPO.setId(id);
        orderPO.setUserId(userId);
        orderPO.setOrderStatus(0);
        orderPO.setCreateTime(new Date());
        orderPO.setUpdateTime(new Date());
        orderMapper.insert(orderPO);
        return id;
    }

    public OrderPO getOrderById(Long orderId) {
        return orderMapper.getById(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(Long orderId, String transactionId) {
        OrderPO orderPO = orderMapper.getById(orderId);
        // 修改订单状态为已支付
        orderPO.setOrderStatus(1);
        int affectedCount = orderMapper.update(orderPO);
        if (affectedCount <= 0) {
            throw new RuntimeException("updateOrder error");
        }
        // 插入到事务日志表
        TransactionLogPO transactionLogPO = new TransactionLogPO();
        transactionLogPO.setId(transactionId);
        transactionLogPO.setBizType(0);
        transactionLogPO.setBizId(String.valueOf(orderPO.getId()));
        transactionLogPO.setCreateTime(new Date());
        transactionLogMapper.insert(transactionLogPO);
    }

    public TransactionLogPO getLogById(String transactionLogId) {
        return transactionLogMapper.getById(transactionLogId);
    }

}
