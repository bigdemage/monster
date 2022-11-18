package com.monster.rocketmq.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;


/**
 * 过滤是broker做的，在发送消息的时候已经过滤好了
 */
public class TagConsumer {

    public static void main(String[] args) throws Exception {
        //创建消费者
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("my-consumer1");
        //指明nameserver
        consumer.setNamesrvAddr(NAME_SERVER_URL);
        //订阅主题--第二个参数是tag，*表示全部接收
        consumer.subscribe("tag_topic","tagA||tagC");
        //创建监听，当broker推送消息过来时调用
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            /**
             * list表示broker推送过来的消息都在list里
             * @param list
             * @param consumeConcurrentlyContext
             * @return
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : list) {
//                    System.out.println("接收消息:"+new String(msg.getBody()));
                    System.out.println("线程---"+Thread.currentThread().getName()+"---接收消息:"+msg.getTags()+"-------"+msg.toString());

                }
                //消费完后告诉服务器成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        System.out.println("消费者已启动");
    }
}
