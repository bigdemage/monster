package com.monster.spring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monster.spring.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

}
