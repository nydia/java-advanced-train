package com.nydia.memorydb.h2;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/30 21:20
 * @Description: H2Properties
 */
public class H2Properties {

    public static final String BASE_DIR = "C:/temp/h2";
    public static final String PORT = "9092";
    public static final String URL = "jdbc:h2:tcp://localhost:"+PORT+"/"+BASE_DIR+"/nydia;MODE=PostgreSQL";
    public static final String USER = "nydia";
    public static final String PASS = "nydia";

}
