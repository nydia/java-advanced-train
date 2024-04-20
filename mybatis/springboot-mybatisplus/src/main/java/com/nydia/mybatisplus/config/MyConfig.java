package com.nydia.mybatisplus.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.nydia.mybatisplus.handler.MyDataPermissionHandler;
import com.nydia.mybatisplus.identifier.CustomIdGenerator;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.nydia.mybatisplus.mapper")
@Configuration
public class MyConfig {
    //乐观锁配置
    @Bean
    public OptimisticLockerInnerInterceptor OptimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    //主键生成
    @Bean
    public IdentifierGenerator idGenerator() {
        return new CustomIdGenerator();
    }

    //@Autowired
    //private MyDataPermissionHandler myDataPermissionHandler;
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 添加数据权限插件
        //DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        // 添加自定义的数据权限处理器
        //dataPermissionInterceptor.setDataPermissionHandler(myDataPermissionHandler);
        //interceptor.addInnerInterceptor(dataPermissionInterceptor);

        // 分页插件
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQL_SERVER));

        //多租户插件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1);
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                return !"user".equalsIgnoreCase(tableName);
            }
        }));
        return interceptor;
    }

}