package com.monster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monster.entity.User;
import com.monster.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;



    public List<User> query() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        return userMapper.selectList(queryWrapper);
    }

    public IPage<User> query(int pageNo, int pageSize) {
        log.info("入参:{},{}",pageNo,pageSize);
        Page<User> page = new Page<>(pageNo,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        return userMapper.selectPage(page,queryWrapper);
    }


}
