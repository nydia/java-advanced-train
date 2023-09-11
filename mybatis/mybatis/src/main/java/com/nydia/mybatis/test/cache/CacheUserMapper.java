package com.nydia.mybatis.test.cache;

import com.nydia.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CacheUserMapper {

    User selectById(Integer id);


}
