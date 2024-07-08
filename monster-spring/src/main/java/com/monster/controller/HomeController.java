package com.monster.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/home")
public class HomeController {

    @Value("${server.port}")
    private String port;


    @GetMapping("/getPort")
    public String getPort(){
        return "配置的端口为:"+port;
    }
}
