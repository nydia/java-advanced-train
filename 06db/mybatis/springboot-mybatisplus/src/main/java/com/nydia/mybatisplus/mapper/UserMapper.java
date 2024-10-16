package com.nydia.mybatisplus.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nydia.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @ResultMap("mybatis-plus_User") // 这个名词格式固定的： mybatis-plus_实体名称
    @Select(" select * from `user` where id = #{id} ")
    User selectOneById(@Param("id") Long id);

    @InterceptorIgnore(tenantLine = "true")
    @Select(" select * from `user` where name = #{name} ")
    User selectByName(@Param("name") Long name);

}