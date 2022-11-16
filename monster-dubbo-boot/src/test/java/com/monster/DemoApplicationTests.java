package com.monster;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.mq.kafka.core.conf.ProducerConfig;
import com.sms.service.SmsSendService;
import com.user.entity.emp.Emp;
import com.user.service.emp.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.Name;

@SpringBootTest
@Slf4j
class DemoApplicationTests {


    @Reference(version = "1.0.0")
    private SmsSendService smsSendService;


    @Autowired
    private ProducerConfig viviPro;


    @Test
    void beanTest(){
        viviPro.getAcks();
    }


    @Test
    void sendSms() throws InterruptedException {


        int total=10;
        int i = total;
            while (i > 0) {
                int result=smsSendService.sendSms("CUSTOMER", "18310973139", "3", true, "WRX_WDNMD", "阿露露");
                log.info("调用结果:{}",result);
                i--;
                log.info("线程:{},已发送{}条,还剩{}条没有发送", Thread.currentThread().getName(),total-i,i);
            }
    }


}
