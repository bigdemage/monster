package com.monster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.ref.SoftReference;

/**
 * @ClassName MonsterApplication
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/7/26/026 13:45
 * @Version 1.0
 **/

@SpringBootApplication(scanBasePackages = {"com.chtwm.sms.producer"})
public class MonsterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonsterApplication.class,args);
        System.out.println("启动成功");
    }
}
