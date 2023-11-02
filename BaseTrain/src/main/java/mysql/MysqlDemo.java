package mysql;

import java.sql.*;
import java.util.Date;

/**
 * Copyright (C) 2021 ShangHai IPS Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of IPS
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description: MysqlDemo
 * @ClassName: MysqlDemo
 * @Auther: Nydia.LHQ
 * @Date: 2021/6/23 13:30
 */
public class MysqlDemo {

    public static void main(String[] args) {
    }

    @SuppressWarnings("Duplicates")
    public static void useStatement() {
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        //String DB_URL = "jdbc:mysql://192.168.8.186:3306/geek?rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai";
        //final String USER = "repl";
        //final String PASS = "@Zz123456";

        String DB_URL = "jdbc:mysql://localhost:3316/db?rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai";
        final String USER = "root";
        final String PASS = "";

        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            conn.setAutoCommit(false);
            // 执行
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            long startTime = new Date().getTime();
            for(int i = 1; i <= 10; i ++){
                String sql = "insert into `geek_user` ( `user_name`, `password`, `nick_name`, `id_card`) values('王五','123456','小五','2344556666')";
                stmt.addBatch(sql);
                if(i % 5 == 0){
                    stmt.executeBatch();
                    conn.commit();//执行完后，手动提交事务
                    stmt.clearBatch();
                    System.out.println("执行到" + i);
                }
            }
            long endTime = new Date().getTime();
            System.out.println("执行时间：" + (endTime - startTime) + "(毫秒)");
            // 完成后关闭
            stmt.close();
            conn.setAutoCommit(true);
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
            try {
                // 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
                if(!conn.isClosed()){
                    conn.rollback();
                    System.out.println("插入失败，回滚！");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
                conn.setAutoCommit(true);
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    @SuppressWarnings("Duplicates")
    public static void usePreparedStatement() {
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        //String DB_URL = "jdbc:mysql://192.168.8.186:3306/geek?rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai";
        //final String USER = "repl";
        //final String PASS = "@Zz123456";

        String DB_URL = "jdbc:mysql://localhost:3316/db?rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai";
        final String USER = "root";
        final String PASS = "";

        Connection conn = null;
        PreparedStatement pstm = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            conn.setAutoCommit(false);
            // 执行
            System.out.println(" 实例化Statement对象...");
            pstm = conn.prepareStatement("insert into `geek_user` ( `user_name`, `password`, `nick_name`, `id_card`) values('王五','123456','小五','2344556666')");
            long startTime = new Date().getTime();
            for(int i = 1; i <= 10; i ++){
                pstm.addBatch();
                if(i % 5 == 0){
                    pstm.executeBatch();
                    conn.commit();//执行完后，手动提交事务
                    pstm.clearBatch();
                    System.out.println("执行到" + i);
                }
            }
            long endTime = new Date().getTime();
            System.out.println("执行时间：" + (endTime - startTime) + "(毫秒)");
            // 完成后关闭
            pstm.close();
            conn.setAutoCommit(true);
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
            try {
                // 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
                if(!conn.isClosed()){
                    conn.rollback();
                    System.out.println("插入失败，回滚！");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(pstm!=null) pstm.close();
                conn.setAutoCommit(true);
                if(conn!=null) conn.close();
            }catch(SQLException se2){
            }
        }
        System.out.println("Goodbye!");
    }


}
