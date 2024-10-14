package com.nydia.mybatis.intercepter;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.nydia.mybatis.entity.BaseEntity;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Date;
import java.util.Properties;

/**
 * MyBatis拦截器四种类型和自定义拦截器的使用流程
 * 一、MyBatis拦截器四种类型的详细解释：
 * 1. ParameterHandler 参数拦截器
 * 2. ResultSetHandler 结果集拦截器
 * 3. StatementHandler 语句拦截器
 * 4. Executor执行拦截器
 * 二、MyBatis拦截器的使用场景
 * 1. 日志记录
 * 2. 性能监控
 * 3. 缓存
 * 4. 权限控制
 * 5. 动态修改SQL
 * 6. 结果集处理
 * 三、自定义 MyBatis 拦截器操作流程：
 * 1. 创建自定义拦截器类
 * 2. 实现 `intercept` 方法
 * 3. 实现 `plugin` 方法
 * 4. 配置拦截器：<plugins> 标签内添加一个 <plugin> 标签，并指定自定义拦截器类的完整路径，也可以使用@component或@Configuration注解注入到IOC容器中
 */
@Configuration
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}),
        @Signature(
                type = StatementHandler.class,
                method = "getBoundSql",
                args = {}),
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        ),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
        )})
public class MybatisIntercepter implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        String origSql = boundSql.getSql();
        Object paramter = boundSql.getParameterObject();

        //修改sql
        sqlModify(sqlCommandType, origSql, paramter);

        return invocation.proceed();
    }

    private void sqlModify(SqlCommandType sqlCommandType,String origSql, Object paramter){
        switch (sqlCommandType){
            case SELECT -> {
            }
            case UPDATE -> {
            }
            case INSERT -> {
                sqlModifyInsert(paramter);
            }
            case DELETE -> {
            }
            case FLUSH -> {
            }
            case UNKNOWN -> {
            }
        }
    }

    private void sqlModifyInsert(Object paramter){
        if(paramter instanceof BaseEntity baseEntity){
            //参数处理
        } else {
            return;
        }
        Field field = ReflectionUtils.findField(BaseEntity.class, "createTime");
        ReflectionUtils.setField(field, baseEntity, new Date());
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }


}
