package com.nydia.memorydb.h2;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author lvhq
 * @date 2024.07.19
 */
@AutoConfiguration
public class JdbcConfig {

    @Bean("h2DataSource")
    public DataSource h2DataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(H2Properties.DRIVER);
        dataSource.setUrl(H2Properties.URL);
        dataSource.setUsername(H2Properties.USER);
        dataSource.setPassword(H2Properties.PASS);
        dataSource.setMaxActive(32);
        dataSource.setMinIdle(4);
        dataSource.setInitialSize(8);
        return dataSource;
    }


}
