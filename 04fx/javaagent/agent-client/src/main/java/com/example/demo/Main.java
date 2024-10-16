package com.example.demo;

/**
 * @Auther: hqlv
 * @Date: 2022/9/5 23:41
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("main start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }


}
