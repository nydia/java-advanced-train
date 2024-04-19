package com.nydia.mybatisplus.config;

import com.baomidou.mybatisplus.extension.ddl.IDdl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

//这个插件没看到支持h2的类型

//@Component
public class H2Ddl implements IDdl {

    @Autowired
    private DataSource dataSource;

    @Override
    public void runScript(Consumer<DataSource> consumer) {
        consumer.accept(dataSource);
    }

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList("db/schema-h2.sql", "db/data-h2.sql");
    }
}