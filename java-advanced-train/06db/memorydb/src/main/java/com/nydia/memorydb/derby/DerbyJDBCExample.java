package com.nydia.memorydb.derby;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * @author lvhq
 * @date 2024.07.03
 */
@Slf4j
public class DerbyJDBCExample {

    /**
     * 简单测试
     */
    public static void temp() {
        String url = "jdbc:derby:sample;create=true";
        String user = "";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "CREATE TABLE MYTABLE (ID INTEGER NOT NULL PRIMARY KEY, NAME VARCHAR(100))";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();

            sql = "INSERT INTO MYTABLE (ID, NAME) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setString(2, "Test");
            stmt.executeUpdate();

            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            log.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建连接
     *
     * @return
     */
    public static Connection getConn() {
        log.info(">>>>>>> create derby connect <<<<<<<<<<");
        String url = "jdbc:derby:sample;create=true";
        String user = "";
        String password = "";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接
     *
     * @return
     */
    public static void closeConn(Connection conn) {
        log.info(">>>>>>> close derby connect <<<<<<<<<<");
        try {
            conn.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
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

    /**
     * 简单查询
     */
    public static void query(Connection conn) {
        try {
            log.info(">>>>>>> derby query <<<<<<<<<<");
            assert conn != null;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT count(1) as cnt from emp");
            if (rs.next()) {
                System.out.println(rs.getInt(1));
            }

            rs.close();
            stm.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * 函数查询
     */
    public static void function(Connection conn) {
        try {
            log.info(">>>>>>> derby function <<<<<<<<<<");
            assert conn != null;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT 1 as num");
            if (rs.next()) {
                int num = rs.getInt("num");
                System.out.println("Selected value: " + num);
            }

            rs.close();
            stm.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * 插入数据
     */
    public static void insert(Connection conn) {
        try {
            log.info(">>>>>>> derby insert <<<<<<<<<<");
            assert conn != null;
            Statement stm = conn.createStatement();
            String sqlInsert = "insert into emp (id,name,address,age) " + "values (100, 'zara', 'ali', 18)";
            sqlInsert += ",(101, 'mahnaz', 'fatma', 25)";
            sqlInsert += ",(102, 'mahnaz', 'fatma', 25)";
            sqlInsert += ",(103, 'zaid', 'khan', 30)";
            sqlInsert += ",(104, 'sumit', 'mittal', 28)";
            stm.executeUpdate(sqlInsert);

            stm.close();
        } catch (SQLException ex) {
           log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //简单测试
        //temp();

        //获取连接
        Connection conn = getConn();

        //初始化schema
        schema(conn);

        //简单查询
        //query(conn);

        //函数查询
        function(conn);

        //插入数据
        //insert(conn);

        //>>>>>>>> close <<<<<
        //关闭连接
        closeConn(conn);
    }

}
