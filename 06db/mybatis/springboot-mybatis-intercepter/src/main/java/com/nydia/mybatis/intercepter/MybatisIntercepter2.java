package com.nydia.mybatis.intercepter;

import cn.hutool.core.util.ReflectUtil;
import com.nydia.mybatis.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Slf4j
public class MybatisIntercepter2 implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        if (args == null || args.length < 2) {
            return invocation.proceed();
        }

        MappedStatement mappedStatement = (MappedStatement) args[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object paramter = args[1];
        BoundSql boundSql = mappedStatement.getBoundSql(paramter);
        String origSql = boundSql.getSql();

        //修改参数
        paramModify(sqlCommandType, origSql, paramter);

        return invocation.proceed();
    }

    private void paramModify(SqlCommandType sqlCommandType, String origSql, Object paramter) {
        switch (sqlCommandType) {
            case SELECT -> {
            }
            case UPDATE -> {
            }
            case INSERT -> {
                paramModifyInsert(paramter, origSql);
            }
            case DELETE -> {
            }
            case FLUSH -> {
            }
            case UNKNOWN -> {
            }
        }
    }

    private void paramModifyInsert(Object paramter, String origSql) {
        if (paramter instanceof BaseEntity paramObj) {
            //参数处理
        } else {
            return;
        }
        Class<? extends BaseEntity> entityClass = paramObj.getClass();
        Field field = ReflectUtil.getField(entityClass, "createBy");
        ReflectUtil.setFieldValue(paramObj, field, "200");

        Field field2 = ReflectUtil.getField(entityClass, "createTime");
        ReflectUtil.setFieldValue(paramObj, field2, new Date());
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


}
