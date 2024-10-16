package com.nydia.mybatisplus.data_security;

import com.baomidou.mybatisplus.core.toolkit.AES;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/4/17 23:20
 * @Description: DataSecurity
 */
public class DataSecurity {

    public static void main(String[] args) {
        // 生成 16 位随机 AES 密钥
        String randomKey = AES.generateRandomKey();
        String url = "jdbc:p6spy:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
        String username = "root";
        String password = "test";

        // 随机密钥加密
        String urlEn = AES.encrypt(url, randomKey);
        String usernameEn = AES.encrypt(username, randomKey);
        String passwordEN = AES.encrypt(password, randomKey);
        System.out.println("urlEn:" + urlEn);
        System.out.println("usernameEn:" + usernameEn);
        System.out.println("passwordEN:" + passwordEN);

    }
}
