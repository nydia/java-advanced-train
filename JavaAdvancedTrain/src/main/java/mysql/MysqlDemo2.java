package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
public class MysqlDemo2 {

    public static void main(String[] args) {
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
            // 执行
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            long startTime = new Date().getTime();
            String sql = "insert into `geek_user` ( `user_name`, `password`, `nick_name`, `id_card`) values('王五','123456','小五','2344556666')";
            stmt.execute(sql);
            long endTime = new Date().getTime();
            System.out.println("执行时间：" + (endTime - startTime) + "(毫秒)");
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            }catch(SQLException se2){
            }
        }
        System.out.println("Goodbye!");
    }
}
