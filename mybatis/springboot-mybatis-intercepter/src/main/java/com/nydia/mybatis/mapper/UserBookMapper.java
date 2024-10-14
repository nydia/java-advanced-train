package com.nydia.mybatis.mapper;

import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.entity.UserBook;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserBookMapper {

    UserBook selectByUserId(Integer userId);

    List<UserBook> selectByNameForMap(String bookName);


}
