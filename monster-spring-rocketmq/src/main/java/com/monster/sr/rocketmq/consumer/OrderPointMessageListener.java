package com.monster.sr.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.monster.sr.common.SnowFlakeIdGenerator;
import com.monster.sr.entity.OrderPO;
import com.monster.sr.entity.PointsPO;
import com.monster.sr.mapper.PointsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 订单消费者 用于添加积分
 */
@Component
@Slf4j
public class OrderPointMessageListener implements MessageListenerConcurrently {


    @Autowired
    private PointsMapper pointsMapper;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt messageExt : msgs) {
                String orderJSON = new String(messageExt.getBody(), "UTF-8");
                log.info("收到订单信息:{}",orderJSON);
                OrderPO orderPO = JSON.parseObject(orderJSON, OrderPO.class);

                // 首先查询是否处理完成
                PointsPO pointsPO = pointsMapper.getByOrderId(orderPO.getId());
                if (pointsPO == null) {
                    Long id = SnowFlakeIdGenerator.getUniqueId(1023, 0);
                    pointsPO = new PointsPO();
                    pointsPO.setId(id);
                    pointsPO.setOrderId(orderPO.getId());
                    pointsPO.setUserId(orderPO.getUserId());
                    // 添加积分数30
                    pointsPO.setPoints(30);
                    pointsPO.setCreateTime(new Date());
                    pointsPO.setRemarks("添加积分数30");
                    pointsMapper.insert(pointsPO);
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.error("consumeMessage error", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

}
