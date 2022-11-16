package com.monster.rocketmq.orderMsg;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import sun.applet.resources.MsgAppletViewer;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 局部顺序订单消费者
 * 所谓局部，就是订单不一定是按顺序接收，但订单里面的物品一定是按顺序接收的
 * 队列不一定是顺序，但队列里的数据一定是顺序拿
 *
 */
public class JubuOrderConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("my-consumer2");
        consumer.setNamesrvAddr(NAME_SERVER_URL);
        //从第几条开始读取
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("order_msg","*");


      //  使用MessageListenerOrderly有序的读取
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                //自动提交
                context.setAutoCommit(true);
                for (MessageExt msg : msgs) {
                    System.out.println("接收消息---"+new String(msg.getBody(), StandardCharsets.UTF_8));
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });


/*        //乱序消费
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {
                    System.out.println("收到消息--"+new String(msg.getBody(), StandardCharsets.UTF_8));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });*/


        //要启动
        consumer.start();

    }
}
