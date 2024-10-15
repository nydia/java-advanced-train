package com.nydia.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nydia.mybatis.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
