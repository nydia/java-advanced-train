package com.nydia.mybatis.test.annatations.cacheNamespace;

import com.nydia.mybatis.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


@CacheNamespace
public interface CacheNamespaceUseAnnatationUserMapper {

    @Select("select id,username,password from tbl_user where id = #{id,jdbcType=INTEGER}")
    @Options(useCache = true)
    User selectById(Integer id);


}
