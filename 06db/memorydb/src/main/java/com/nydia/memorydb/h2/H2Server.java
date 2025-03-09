package com.nydia.memorydb.h2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.reflections.Reflections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * H2 TCP服务启动
 *
 * @author lvhq
 * @date 2024.07.03
 */
@Slf4j
@Component
@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class H2Server implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // 启动 TCP 服务器
        log.info("H2 Memory Server starting......");
        Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", H2Properties.TCP_PORT, "-ifNotExists", "-pg").start();
        log.info("H2 Memory Server started success.");

        //初始化H2函数
        log.info("H2 Function init starting......");
        h2FunctionInit();
        log.info("H2 Function init success.");

        //初始化dual表
        log.info("H2 dual table init starting......");
        createPreTable();
        log.info("H2 dual table init success.");
    }

    /**
     * H2 函数初始化
     */
    private void h2FunctionInit() {
        if (!H2Properties.LOAD_FUNC_FLAG) {
            log.info("没有开启函数加载开关");
        }
        //要扫描的包名
        String packageName = H2Properties.FUNC_PACKAGE;
        Reflections f = new Reflections(packageName);
        Set<Class<?>> classSet = f.getTypesAnnotatedWith(RuleFunc.class);
        if (CollUtil.isEmpty(classSet)) {
            log.info("没有需要加载的内置函数");
        }
        for (Class<?> clazz : classSet) {
            String className = clazz.getCanonicalName();

            RuleFunc ruleFuncClass = clazz.getAnnotation(RuleFunc.class);
            String codeClass = ruleFuncClass.code();
            String typeClass = ruleFuncClass.type();
            if (StrUtil.isNotBlank(codeClass) && StrUtil.isNotBlank(typeClass)) {
                createFunc(codeClass, typeClass, className);
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (null != method && null != method.getAnnotation(RuleFunc.class)) {
                    String execute = className.concat(".").concat(method.getName());
                    RuleFunc ruleFuncMethod = method.getAnnotation(RuleFunc.class);
                    String code = ruleFuncMethod.code();
                    String type = ruleFuncMethod.type();
                    if (StrUtil.isBlank(code) || StrUtil.isBlank(type)) {
                        log.error("函数{}没有定义函数编码或者类型,code={},type={}", execute, code, type);
                        continue;
                    }
                    createFunc(code, type, execute);
                }
            }
        }


    }

    /**
     * 创建函数
     *
     * @param code    函数编码
     * @param type    函数类型
     * @param execute 函数执行
     */
    private void createFunc(String code, String type, String execute) {
        String sql = "";
        if (type.equals(H2Properties.FUNC_TYPE_SCALAR)) {
            sql = "CREATE ALIAS if not exists ";
        } else if (type.equals(H2Properties.FUNC_TYPE_AGGREGATE)) {
            sql = "CREATE AGGREGATE if not exists ";
        }
        sql += code;
        sql += " FOR \"";
        sql += execute;
        sql += "\"";
        JdbcUtil.createFunc(sql);

    }

    private void createPreTable() {
        //删除表
        String dropSql = "drop table if exists dual";
        JdbcUtil.dropTable(dropSql);

        //创建表
        String createSql = "create table if not exists dual(id varchar(40) not null)";
        JdbcUtil.createTable(createSql);

        //清除表数据
        String deleteSql = "delete from dual";
        JdbcUtil.batchDelete(deleteSql, null);

        //插入初始化数据
        String insertSql = "insert into dual (id) values (:id)";
        Map[] dataList = new Map[1];
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", "1");
        dataList[0] = dataMap;
        JdbcUtil.batchInsert(insertSql, dataList);
    }

}
