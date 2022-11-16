package com.monster.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName JieShou10
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/11/17/017 13:59
 * @Version 1.0
 **/
public class JieShou10 {

    public static void main(String[] args) {
        new Thread(()->{
            try {
                Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
                String _topic="wrx_wudi";
                topicCountMap.put(_topic, new Integer(1));
                Properties props = new Properties();
                props.put("zookeeper.connect", "172.16.198.21:2181,172.16.198.22:2181,172.16.198.20:2181");
                props.put("group.id", "wrx_wudi");
                ConsumerConfig config=new ConsumerConfig(props);
                ConsumerConnector consumer= Consumer.createJavaConsumerConnector(config);
                Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
                KafkaStream<byte[], byte[]> stream = consumerMap.get(_topic).get(0);
                ConsumerIterator<byte[], byte[]> it = stream.iterator();
                System.out.println("开始读取kafka消息,消息主题:"+_topic);
                while (it.hasNext()) {
                    MessageAndMetadata<byte[], byte[]> mam = it.next();
                    String message = new String(mam.message(), Charset.forName("UTF-8"));
                    System.out.println(String.format("消费消息:Thread %s: partition[%s],"
                                    + "offset[%s],message[ %s ]" ,
                            Thread.currentThread().getName(),
                            mam.partition(), mam.offset(),message));
                }
                System.out.println("退出循环");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
