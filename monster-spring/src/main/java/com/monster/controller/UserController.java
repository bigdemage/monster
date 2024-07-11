package com.monster.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monster.entity.User;
import com.monster.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/all")
    public List<User> getPort(){
        return userService.query();
    }

    @GetMapping("/findByPage")
    public IPage<User> getPort(@RequestParam Integer page, @RequestParam Integer size){
        return userService.query(page,size);
    }
}
