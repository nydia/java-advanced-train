package com.nydia.mybatisplus.plugins;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//多租户查询

@SpringBootTest
public class TenantTest {

    @Autowired
    private UserMapper userMapper;

    //多租户查询
    @Test
    public void test_tenantQuery() {
        System.out.println(("----- tenantQuery method test ------"));
        //当前租户的tenant_id=1，只能查询tenant_id=1的数据

        // 从数据库获取表所有记录,做数据处理
        userMapper.selectList(Wrappers.emptyWrapper(), resultContext -> {
            // 依次得到每条业务记录
            System.out.println("当前处理第" + resultContext.getResultCount() + "条记录.");
            //做自己的业务处理,比如分发任务
        });
    }


}