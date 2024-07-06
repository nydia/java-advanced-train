package com.nydia.memorydb.calcite;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.tools.Frameworks;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class CalciteH2Example {

    public static void main(String[] args) {
        try {
            // 创建 H2 数据源
            DataSource h2DataSource = Frameworks.createDataSource(
                    "jdbc:h2:mem:test;MODE=PostgreSQL",
                    "sa",
                    "");

            // 创建 Calcite 架构并添加 H2 数据源
            SchemaPlus rootSchema = Frameworks.newConfigBuilder()
                    .defaultSchema(Frameworks.createSchema("nydia", h2DataSource))
                    .build()
                    .rootSchema();

            // 连接到 Calcite
            Properties info = new Properties();
            info.setProperty("lex", "JAVA");
            Connection conn = DriverManager.getConnection("jdbc:calcite:", info);

            // 转换成 CalciteConnection
            CalciteConnection calciteConnection = conn.unwrap(CalciteConnection.class);
            calciteConnection.setRootSchema(rootSchema);

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