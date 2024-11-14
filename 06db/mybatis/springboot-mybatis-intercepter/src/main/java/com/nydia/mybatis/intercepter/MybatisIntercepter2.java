package com.nydia.mybatis.intercepter;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.nydia.mybatis.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NamedExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

@Intercepts({
//        @Signature(
//                type = StatementHandler.class,
//                method = "prepare",
//                args = {Connection.class, Integer.class}),
        @Signature(
                type = StatementHandler.class,
                method = "update",
                args = {Statement.class})
})
@Slf4j
public class MybatisIntercepter2 implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println(1);

        boolean result = true;
        if (result) {
            return invocation.proceed();
        }

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        String origSql = boundSql.getSql();
        Object paramter = boundSql.getParameterObject();

        //修改sql
        String newSql = sqlModify(sqlCommandType, origSql, paramter);
        if (newSql != null && newSql.length() > 0) {
            metaObject.setValue("boundSql.sql", newSql);
        }

        return invocation.proceed();
    }

    private String sqlModify(SqlCommandType sqlCommandType, String origSql, Object paramter) {
        switch (sqlCommandType) {
            case SELECT -> {
            }
            case UPDATE -> {
            }
            case INSERT -> {
                return sqlModifyInsert(paramter, origSql);
            }
            case DELETE -> {
            }
            case FLUSH -> {
            }
            case UNKNOWN -> {
            }
        }
        return "";
    }

    private String sqlModifyInsert(Object paramter, String origSql) {
        String newSql = "";
        if (paramter instanceof BaseEntity baseEntity) {
            //参数处理
        } else {
            return newSql;
        }
        Field field = ReflectionUtils.findField(BaseEntity.class, "createTime");
        ReflectionUtils.setField(field, baseEntity, new Date());

        return sqlModifyInsert(origSql);
    }

    private String sqlModifyInsert(String origSql) {
        String newSql = "";
        try {
            Insert insert = (Insert) CCJSqlParserUtil.parse(origSql);
            // insert.getColumns().add(new Column("create_time"));
            insert.getColumns().add(new Column("uuid"));
            insert.getItemsList(ExpressionList.class).accept(new ItemsListVisitor() {
                @Override
                public void visit(SubSelect subSelect) {
                }

                @Override
                public void visit(ExpressionList expressionList) {
                    //expressionList.getExpressions().add(new DateValue(new java.sql.Date(new Date().getTime())));
                    //expressionList.getExpressions().add(new StringValue(UUID.randomUUID().toString()));
                    expressionList.getExpressions().add(new StringValue(UUID.randomUUID().toString()));
                }

                @Override
                public void visit(NamedExpressionList namedExpressionList) {

                }

                @Override
                public void visit(MultiExpressionList multiExpressionList) {
                }
            });
            newSql = insert.toString();
        } catch (Exception e) {
            log.error("解析插入sql错误");
        }
        return newSql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


}
