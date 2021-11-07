package com.nydia.modules.mapper.master;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HealthCheckDao extends Mapper {

    @Select(value = "SELECT 1 from dual")
    int healthChecek();

}
