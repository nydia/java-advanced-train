package com.nydia.mybatisplus.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import com.nydia.mybatisplus.handler.MyDataPermissionHandler;
import com.nydia.mybatisplus.identifier.CustomIdGenerator;
import com.nydia.mybatisplus.interceptor.SqlLoggerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.schema.Column;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

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

        //多租户插件(插件BlockAttackInnerInterceptor 和 TenantLineInnerInterceptor 插件冲突，因为多租户会默认加一个租户的条件，无法做到全表删除更新)
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                Long tenantId = 1l;//可以从上下文中取到
                return new LongValue(tenantId);
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                //return !"user".equalsIgnoreCase(tableName);
                return false;
            }

            @Override
            public String getTenantIdColumn() {
                //默认 tenant_id
                return TenantLineHandler.super.getTenantIdColumn();
            }

            @Override
            public boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
                return TenantLineHandler.super.ignoreInsert(columns, tenantIdColumn);
            }
        }));

        //防止全表更新--要测试的话需要把上面的多租户插件注释 (插件BlockAttackInnerInterceptor 和 TenantLineInnerInterceptor 插件冲突，因为多租户会默认加一个租户的条件，无法做到全表删除更新)
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        //动态表名插件，这个测试的时候在开启，不然参数要特的那个设置。使用专用的测试用例：DynamicTableNameInnerInterceptorTest
        //interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor());

        //数据变动记录日志（官方自带）
        interceptor.addInnerInterceptor(new DataChangeRecorderInnerInterceptor().openBatchUpdateLimitation());

        //打印操作日志

        return interceptor;
    }

    //mybatis自己的拦截器
    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory) {
        //实例化插件
        SqlLoggerInterceptor sqlInterceptor = new SqlLoggerInterceptor();
        //创建属性值
        Properties properties = new Properties();
        properties.setProperty("prop1", "value1");
        //将属性值设置到插件中
        sqlInterceptor.setProperties(properties);
        //将插件添加到SqlSessionFactory工厂
        sqlSessionFactory.getConfiguration().addInterceptor(sqlInterceptor);
        return "interceptor";
    }

    private DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor() {
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            // 获取参数方法
            Map<String, Object> paramMap = RequestDataHelper.getRequestData();
            paramMap.forEach((k, v) -> System.err.println(k + "----" + v));

            String year = "_2018";
            int random = new Random().nextInt(10);
            if (random % 2 == 1) {
                year = "_2019";
            }
            return tableName + year;
        });
        return dynamicTableNameInnerInterceptor;
    }


}