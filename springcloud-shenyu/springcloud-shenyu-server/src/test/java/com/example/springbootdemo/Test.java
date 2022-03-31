package com.example.springbootdemo;

import java.util.StringTokenizer;

/**
 * @Auther: hqlv
 * @Date: 2021/8/11 23:50
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        String addr = "192.168.1.1;192.168.1.2,192.168.1.3";
        StringTokenizer tokenizer = new StringTokenizer(addr, ",;");
        while (tokenizer.hasMoreTokens()) {
            String serverAddr = tokenizer.nextToken().trim();
            System.out.println(serverAddr);
        }
    }

}
