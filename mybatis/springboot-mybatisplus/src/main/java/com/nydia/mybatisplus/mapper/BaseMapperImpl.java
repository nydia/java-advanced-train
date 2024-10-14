package com.nydia.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public abstract class BaseMapperImpl<T> implements BaseMapper<T> {

    @Override
    public List<T> selectListSpecial(@Param("ew") Wrapper<T> queryWrapper, SFunction<T, ?> column, Object paramterValue) {
        LambdaMeta meta = LambdaUtils.extract(column);
        T entityClass = queryWrapper.getEntity();
        
        return null;
    }


}