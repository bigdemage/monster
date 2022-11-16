package com.monster.test.bingfasms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sms.service.SmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ConcurrenceSendSmsTests {


    @Reference(version = "2.0.0")
    private SmsSendService smsSendService;


    @Test
    void sendSms() throws InterruptedException {

        int total=5;
        int i = total;
            while (i > 0) {
                int result=smsSendService.sendSms("CUSTOMER", "18310973139", "3", true, "WRX_WDNMD", "萨斯噶");
                log.info("调用结果:{}",result);
                i--;
                log.info("线程:{},已发送{}条,还剩{}条没有发送", Thread.currentThread().getName(),total-i,i);
                Thread.sleep(RandomUtils.nextInt(100,500));
            }
    }


}
