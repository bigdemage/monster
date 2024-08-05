package com.monster.sr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.monster.sr.mapper")
public class SpringRocketMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRocketMqApplication.class, args);
	}

}
