package com.nydia.memorydb.h2;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/13 21:10
 * @Description: H2Connect
 */
@Slf4j
public class H2Connect {

    /**
     * 连接
     *
     * @return
     */
    public static Connection connect() {
        String url = "jdbc:h2:mem:nydia;DB_CLOSE_DELAY=-1";
        try {
            return DriverManager.getConnection(url, "sa", "");
        } catch (SQLException ex) {
            log.info(ex.getMessage(), ex);
        }
        return null;
    }

}
