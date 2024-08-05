package com.nydia.memorydb.calcite;

import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.jdbc.CalciteConnection;

import java.sql.*;
import java.util.Properties;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/6 20:09
 * @Description: CalciteJDBCExample
 */
@Slf4j
public class CalciteJDBCConnection {

    public static CalciteConnection getConn(){
        CalciteConnection calciteConnection = null;
        try {
            Class.forName("org.apache.calcite.jdbc.Driver");
            Properties info = new Properties();
            info.setProperty("lex", "JAVA");
            Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
            calciteConnection = connection.unwrap(CalciteConnection.class);

        }catch (SQLException ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return calciteConnection;
    }


}
