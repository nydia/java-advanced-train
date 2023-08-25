package com.nydia.mybatis.mapper;

import com.nydia.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface UserMapper {

    User selectById(Integer id);

    Map selectByIdForMap(Integer id);

    int insert(User user);

    int insert2(User user);

    int insert3(User user);

    User lasyLoadQuery(Integer id);

    User selectByIdForResultSet(Integer id);

    List<User> selectByNameForResultSet(String username);

}
