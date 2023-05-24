package com.nydia.mybatis.mapper;

import com.nydia.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component("userMapper")
@Mapper
public interface UserMapper {

    User selectById(Integer id);

}
