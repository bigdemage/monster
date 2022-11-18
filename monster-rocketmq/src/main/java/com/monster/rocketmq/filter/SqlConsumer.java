package com.monster.rocketmq.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;


/**
 * 过滤是broker做的，在发送消息的时候已经过滤好了
 * 引用自rocketMq中文文档语法https://yuya008.gitbooks.io/apache-rocketmq/content/guo-lv-qi-shi-li.html
 *
 */
public class SqlConsumer {

    public static void main(String[] args) throws Exception {
        //创建消费者
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("my-consumer1");
        //指明nameserver
        consumer.setNamesrvAddr(NAME_SERVER_URL);
        //订阅主题
        String sql ="(TAGS is not null and TAGS in ('tagA')) and (a between 1 and 5)";
        consumer.subscribe("sql_topic", MessageSelector.bySql(sql));
        //创建监听，当broker推送消息过来时调用
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : list) {
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
