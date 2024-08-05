package com.nydia.memorydb.h2;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/13 21:10
 * @Description: H2Connect
 */
@Slf4j
public class H2ConnectServer {

    /**
     * 连接
     *
     * @return
     */
    public static Connection connect() {
        try {
            return DriverManager.getConnection(H2Properties.URL,
                    H2Properties.USER, H2Properties.PASS);
        } catch (SQLException ex) {
            log.info(ex.getMessage(), ex);
        }
        return null;
    }

    public static void temp(){
        try {
            Connection con = connect();
            assert con != null;
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT 1+1");
            if (rs.next()) {
                System.out.println(rs.getInt(1));
            }
            rs.close();;
            stm.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        temp();
    }

}
