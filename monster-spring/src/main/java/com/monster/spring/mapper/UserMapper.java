package com.monster.spring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monster.spring.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Mapper 注解,使当前的Mapper 接口,被Spring进行管理,不然需要在,
 * 启动类上声明 @MapperScan("com.wsm.mapper") 类扫描指定包下,mapper接口文件;
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

