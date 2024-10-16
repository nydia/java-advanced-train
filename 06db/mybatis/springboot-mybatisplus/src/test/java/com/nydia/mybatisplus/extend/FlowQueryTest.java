package com.nydia.mybatisplus.extend;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

//流式查询

@SpringBootTest
public class FlowQueryTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_flowQuery() {
        System.out.println(("----- flowQuery method test ------"));

        // 从数据库获取表所有记录,做数据处理
        userMapper.selectList(Wrappers.emptyWrapper(), resultContext -> {
            // 依次得到每条业务记录
            System.out.println("当前处理第" + resultContext.getResultCount() + "条记录.");
            //做自己的业务处理,比如分发任务
        });
    }


}