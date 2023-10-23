package com.nydia.demo.grpc.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nydia.demo.grpc.dao.entity.UserDemo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
@Mapper
public interface UserDemoMapper extends BaseMapper<UserDemo> {

}
