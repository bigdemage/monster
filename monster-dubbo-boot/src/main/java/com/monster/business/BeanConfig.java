package com.monster.business;

import com.mq.kafka.core.conf.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.rmi.PortableRemoteObject;
import java.util.Properties;

/**
 * @ClassName BeanConfig
 * @Deacription TODO
 * @Author wrx
 * @Date 2022/7/5/005 14:21
 * @Version 1.0
 **/
@Component
public class BeanConfig {

    @Value("${serializer.class}")
    private String serializerClass;

    @Value("${metadata.broker.list}")
    private String metadataBrokerList;

    public ProducerConfig kafkaProducer() {
        ProducerConfig config=new ProducerConfig();
        config.setKeySerializer(serializerClass);
        config.setValueSerializer(serializerClass);
        config.setBootstrapServers(metadataBrokerList);
        return config;
    }

    @Bean(name ="viviPro")
    public ProducerConfig vivi(){
        ProducerConfig producerConfig=kafkaProducer();
        producerConfig.setAcks("2");
        producerConfig.setBatchSize("33757");
        producerConfig.setBufferMemory(22L);
        return producerConfig;
    }

    @Bean(name = "kikiPro")
    public ProducerConfig kiki(){
        ProducerConfig producerConfig=kafkaProducer();
        producerConfig.setAcks("3");
        producerConfig.setBatchSize("8866");
        producerConfig.setBufferMemory(33L);
        return producerConfig;
    }
}
