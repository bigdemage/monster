package com.monster.rocketmq.delay;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 广播接收，每一个consumer都收一遍
 */
public class DelayConsumer {

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("broad_group");

        consumer.setNamesrvAddr(NAME_SERVER_URL);
        //从第一条开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("delay_topic","*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(new String(msg.getBody())+"---------"+(System.currentTimeMillis()-msg.getStoreTimestamp())+"ms later");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

    }


}
