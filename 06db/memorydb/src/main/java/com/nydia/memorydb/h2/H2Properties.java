package com.nydia.memorydb.h2;

/**
 * @author lvhq
 * @date 2024.07.17
 */
public class H2Properties {

    public final static String DRIVER = "org.h2.Driver";
    public final static String URL = "jdbc:h2:tcp://localhost:9099/~/nydia;MODE=PostgreSQL";
    public final static String TCP_PORT = "9099";
    public final static String USER = "nydia";
    public final static String PASS = "nydia";
    /**
     * h2 自定义函数加载包
     */
    public final static String FUNC_PACKAGE = "com.nydia.memorydb.h2.functions";
    /**ß
     * 是否加载函数开关
     */
    public final static Boolean LOAD_FUNC_FLAG = true;
    /**
     * H2 标量函数
     */
    public final static String FUNC_TYPE_SCALAR = "1";
    /**
     * H2 聚合函数
     */
    public final static String FUNC_TYPE_AGGREGATE = "2";


}
