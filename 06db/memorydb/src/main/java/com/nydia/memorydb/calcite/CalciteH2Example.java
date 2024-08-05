package com.nydia.memorydb.calcite;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


public class CalciteH2Example {


    public static DataSource createH2DataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:nydia;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    public static void main(String[] args) {
        try {

            // 创建 H2 数据源
            // Create the H2 data source
            DataSource h2DataSource = createH2DataSource();

            // 连接到 Calcite
            // 设置连接参数
            Properties info = new Properties();
            // 不区分sql大小写
            info.setProperty("caseSensitive", "false");
            Connection conn = DriverManager.getConnection("jdbc:calcite:", info);
            // 转换成 CalciteConnection
            CalciteConnection calciteConnection = conn.unwrap(CalciteConnection.class);

            // 构建RootSchema，在Calcite中，RootSchema是所有数据源schema的parent，多个不同数据源schema可以挂在同一个RootSchema下, 以实现查询不同数据源的目的
            // Register H2 schema in Calcite
            SchemaPlus rootSchema = calciteConnection.getRootSchema();

            // 将不同数据源schema挂载到RootSchema
            //Schema hrs = new ReflectiveSchema(new MemSchema());
            //rootSchema.add("hr", hrs);//对象
            //rootSchema.add("csv", csvSchema);//csv

            // 执行 SQL 查询
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM nydia.mytable");

            // 处理结果集
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            // 关闭资源
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}