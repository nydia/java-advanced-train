package com.nydia.mybatis.mapper;

import com.nydia.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface UserMapper {

    User selectById(Integer id);

    Map selectByIdForMap(Integer id);

    int insert(User user);

    int insert2(User user);

    User lasyLoadQuery(Integer id);

}
