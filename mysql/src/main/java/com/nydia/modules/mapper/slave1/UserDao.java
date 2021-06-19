package com.nydia.modules.mapper.slave1;

import com.nydia.modules.entity.slave1.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日志Dao
 */
@Component
@Mapper
public interface UserDao {

    @Select({ "select user_id, user_name, password, nick_name, id_card from  geek_user" })
    List<User> selectUser(User user);

}
