package com.monster.sr.controller;

import com.alibaba.fastjson.JSON;
import com.monster.sr.entity.OrderPO;
import com.monster.sr.result.ResponseEntity;
import com.monster.sr.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.monster.sr.common.Constant.ORDER_TOPIC;

/**
 * Created by zhangyong on 2023/7/5.
 */
@Controller
@Slf4j
public class OrderController {


    @Autowired
    OrderService orderService;

    @Autowired
    private TransactionMQProducer producer;

    @GetMapping("/order/insertPayOrder")
    @ResponseBody
    public ResponseEntity insert(Long userId) {
        try {
            Long orderId = orderService.insertOrder(userId);
            return ResponseEntity.successResult(orderId);
        } catch (Exception e) {
            log.error("insertPayOrder error:", e);
            return ResponseEntity.failResult("生成订单失败");
        }
    }

    @GetMapping("/order/updatePayOrderSuccess")
    @ResponseBody
    public ResponseEntity updatePayOrderSuccess(Long orderId) {
        try {
            OrderPO orderPO = orderService.getOrderById(orderId);
            if(orderPO ==null) return ResponseEntity.failResult("订单不存在");
            // 发送事务消息
            Message message = new Message(ORDER_TOPIC, JSON.toJSONString(orderPO).getBytes());
            TransactionSendResult sendResult = this.producer.sendMessageInTransaction(message, null);;
            if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                return ResponseEntity.successResult(orderId);
            }
            return ResponseEntity.failResult("修改订单失败");
        } catch (Exception e) {
            log.error("updatePayOrderSuccess error:", e);
            return ResponseEntity.failResult("修改订单失败");
        }
    }

}
