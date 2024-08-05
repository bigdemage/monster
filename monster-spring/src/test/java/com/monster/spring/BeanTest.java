package com.monster.spring;

import com.monster.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Nullable;


@SpringBootTest
@Slf4j
public class BeanTest {


    @Test
    public void getBean(){

    log.info("你好");
    }

}
