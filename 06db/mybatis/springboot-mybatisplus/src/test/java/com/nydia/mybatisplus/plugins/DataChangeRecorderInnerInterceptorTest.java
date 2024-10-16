package com.nydia.mybatisplus.plugins;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataChangeRecorderInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.nydia.mybatisplus.config.RequestDataHelper;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

//数据变动记录日志

@SpringBootTest
public class DataChangeRecorderInnerInterceptorTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public void initBatchLimitation(int limitation) {
        if (sqlSessionFactory instanceof DefaultSqlSessionFactory) {
            Configuration configuration = sqlSessionFactory.getConfiguration();
            for (Interceptor interceptor : configuration.getInterceptors()) {
                if (interceptor instanceof MybatisPlusInterceptor) {
                    List<InnerInterceptor> innerInterceptors = ((MybatisPlusInterceptor) interceptor).getInterceptors();
                    for (InnerInterceptor innerInterceptor : innerInterceptors) {
                        if (innerInterceptor instanceof DataChangeRecorderInnerInterceptor) {
                            ((DataChangeRecorderInnerInterceptor) innerInterceptor).setBatchUpdateLimit(limitation).openBatchUpdateLimitation();
                        }
                    }
                }
            }
        }
    }

    //测试示例
    @Test
    public void test_DataChangeRecorderInnerInterceptor() {
        System.out.println(("----- DataChangeRecorderInnerInterceptor method test ------"));

        //设置阈值
        initBatchLimitation(3);

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("name", "2024test");
        int result = userMapper.update(updateWrapper);
        System.out.println("更新结果====>" + result);
    }


}