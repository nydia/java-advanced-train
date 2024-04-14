package com.nydia.mybatisplus.identifier;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.nydia.mybatisplus.utils.UuidUtil;

//自定义主键策略： https://baomidou.com/pages/568eb2/#spring-boot

public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        String bizKey = entity.getClass().getName();
        //根据bizKey调用分布式ID生成，下面是临时的方法，后期可以改成zk这样的分布式服务去保住id唯一
        long id = UuidUtil.getLong();
        //返回生成的id值即可.
        return id;
    }

}