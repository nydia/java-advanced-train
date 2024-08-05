package com.nydia.memorydb.calcite;

import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.*;
import java.util.Properties;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/6 20:09
 * @Description: CalciteJDBCExample
 */
@Slf4j
public class CalciteJDBCExample {

    public static CalciteConnection getConn(){
        CalciteConnection calciteConnection = null;
        try {
            Class.forName("org.apache.calcite.jdbc.Driver");
            Properties info = new Properties();
            info.setProperty("lex", "JAVA");
            Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
            calciteConnection = connection.unwrap(CalciteConnection.class);

            //SchemaPlus rootSchema = calciteConnection.getRootSchema();
            //Schema schema = new ReflectiveSchema(new HrSchema());
            //rootSchema.add("hr", schema);

        }catch (SQLException ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return calciteConnection;
    }

    /**
     * 初始化table
     */
    public static void schema(Connection conn) {
        try {
            log.info(">>>>>>> derby schema <<<<<<<<<<");
            Statement stm = conn.createStatement();

            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, "APP", "EMP", new String[]{"TABLE"});
            if (!rs.next()) { // 如果表不存在
                String sql = "CREATE TABLE EMP " +
                        "(id INTEGER not null, " +
                        " name VARCHAR(255), " +
                        " address VARCHAR(255), " +
                        " age INTEGER, " +
                        " PRIMARY KEY ( id ))";
                stm.executeUpdate(sql);
                log.info("Table EMP created successfully.");
            } else {
                log.info("Table EMP already exists.");
            }

            stm.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getConn();
    }

}
