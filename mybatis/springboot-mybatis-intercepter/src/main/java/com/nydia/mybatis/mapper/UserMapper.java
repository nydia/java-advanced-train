package com.nydia.mybatis.mapper;

import com.nydia.mybatis.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    int insert(User user);

    int update(User user);

    User selectById(Integer id);

}
