package com.nydia.mybatisplus.plugins;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.nydia.mybatisplus.config.RequestDataHelper;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

//动态表插件

@SpringBootTest
public class DynamicTableNameInnerInterceptorTest {

    @Autowired
    private UserMapper userMapper;

    //测试示例
    //打印出来的SQL:  Execute SQL：SELECT id, name, email, org_ids, update_time, create_by, version, amount, 'interval', sex, del_f, tenant_id FROM `user`_2018 WHERE id = 1 AND del_f = '0' AND tenant_id = 1
    @Test
    public void test_DynamicTableNameInnerInterceptor() {
        System.out.println(("----- DynamicTableNameInnerInterceptor method test ------"));
        RequestDataHelper.setRequestData(new HashMap<String, Object>() {{
            put("id", 123);
            put("hello", "tomcat");
            put("name", "汤姆凯特");
        }});
        // 自己去观察打印 SQL 目前随机访问 user_2018  user_2019 表
        for (int i = 0; i < 6; i++) {
            User user = userMapper.selectById(1);
            System.err.println(user.getName());
        }
    }


}