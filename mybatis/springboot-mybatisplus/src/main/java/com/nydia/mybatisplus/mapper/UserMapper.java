package com.nydia.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nydia.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @ResultMap("mybatis-plus_User11")
    @Select(" select * from `user` where id = #{id} ")
    User selectOneById(@Param("id") Long id);

}