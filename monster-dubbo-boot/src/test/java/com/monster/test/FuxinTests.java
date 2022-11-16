package com.monster.test;

import com.chtwm.htool.business.enums.SysChannelEnum;
import com.chtwm.sms.common.entity.Sms;
import com.chtwm.sms.common.enums.SmsTypeEnum;
import com.chtwm.sms.common.exception.SmsIllegalArgumentException;
import com.chtwm.sms.producer.api.SmsSender;
import com.common.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
class FuxinTests {


    @Autowired
    private SmsSender smsSender;


    @Test
    void sendSms() throws  SmsIllegalArgumentException {
        Sms sms =new Sms();
        sms.setBusinessNo(RandomUtil.getRandom32Str());
        sms.setPhones(Arrays.asList("18310973134"));
        sms.setSmsTypeEnum(SmsTypeEnum.FUXIN);
        sms.setSysChannelEnum(SysChannelEnum.CRM);
        sms.setSmsContent("geiNiLianLeHai");
        sms.setTemplateCode("shaluan_0");
        smsSender.sendFuxintSms(sms,null);
    }


}
