package com.nydia.memorydb.calcite;

import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;
import org.springframework.util.StopWatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class CalciteDemo {
    Properties info;
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    public void testSelectEntity() throws Exception{
        // 构造Schema
        Schema hrs = new ReflectiveSchema(new MemSchema());
        // 设置连接参数
        info = new Properties();
    	info.setProperty("caseSensitive", "false");		// SQL大小写不敏感
        // 建立连接
        connection = DriverManager.getConnection("jdbc:calcite:", info);
        // 取得Calcite连接
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
		// 取得RootScheam RootSchema是所有Schema的父Schema
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        // 添加schema
        rootSchema.add("hr", hrs);

        StopWatch sw = new StopWatch();
        sw.start();
        // 编写SQL
        //String sql = "select * from HR.DEPTS";
        String sql = "select * from HR.EMPS";
        // 执行查询
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        // 打印结果
        printRs(resultSet);sw.stop();
        log.info("run sql: " + sw.getLastTaskTimeMillis());

        sw.start();
        sql = "select * from HR.EMPS where name like '%Sue%'";
        // 执行查询
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        // 打印结果
        printRs(resultSet);
        sw.stop();
        log.info("run sql: " + sw.getLastTaskTimeMillis());

        sw.start();
        sql = "select * from HR.EMPS where deptno=1 order by empid";
        // 执行查询
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        // 打印结果
        printRs(resultSet);
        sw.stop();
        log.info("run sql: " + sw.getLastTaskTimeMillis());
        
        resultSet.close();
        statement.close();
        connection.close();
    }

    public void testSelectObject() throws Exception{
        // 构造Schema
        MemorySchema m = new MemorySchema();
        m.getTableMap().put("test1", createTable());
        m.addFunction("minus1", ScalarFunctionImpl.create(CustomFunctions.class,"minus1"));

        // 设置连接参数
        info = new Properties();
        info.setProperty("caseSensitive", "false");		// SQL大小写不敏感
        // 建立连接
        connection = DriverManager.getConnection("jdbc:calcite:", info);
        // 取得Calcite连接
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        // 取得RootScheam RootSchema是所有Schema的父Schema
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        // 添加schema
        rootSchema.add("m", m);

        StopWatch sw = new StopWatch();
        sw.start();
        // 编写SQL
        String sql = "select * from m.test1 where COALESCE (id, 0) <> 2 order by id asc";
        // 执行查询
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        // 打印结果
        printRs(resultSet);
        sw.stop();
        log.info("run sql: " + sw.getLastTaskTimeMillis());

        sw.start();
        // 编写SQL
        sql = "select * from m.test1 where name='c' order by id asc";
        // 执行查询
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        // 打印结果
        printRs(resultSet);
        sw.stop();
        log.info("run sql: " + sw.getLastTaskTimeMillis());

        sw.start();
        // 编写SQL
        sql = "select 1+2*3 as field1, m.minus1(1+2*3) as field2";
        // 执行查询
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        // 打印结果
        printRs(resultSet);
        sw.stop();
        log.info("run sql: " + sw.getLastTaskTimeMillis());

        resultSet.close();
        statement.close();
        connection.close();
    }

    private void printRs(ResultSet resultSet) throws Exception{
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(", ");
                String columnValue = resultSet.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
            }
            System.out.println();
        }
    }

    private MemoryTable createTable() {
        List<MemoryColumn> meta = new ArrayList<>();
        List<List<Object>> source = new ArrayList<>();
        MemoryColumn id = new MemoryColumn("id", Long.class);
        MemoryColumn name = new MemoryColumn("name", String.class);
        MemoryColumn age = new MemoryColumn("age", Integer.class);
        meta.add(id);meta.add(name);meta.add(age);

        List<Object> line1 = new ArrayList<Object>(){
            {
                add(1L);
                add("a");
                add(10);
            }
        };
        List<Object> line2 = new ArrayList<Object>(){
            {
                add(2L);
                add("b");
                add(20);
            }
        };
        List<Object> line3 = new ArrayList<Object>(){
            {
                add(3L);
                add("c");
                add(30);
            }
        };
        List<Object> line4 = new ArrayList<Object>(){
            {
                add(null);
                add("c");
                add(40);
            }
        };
        source.add(line1);source.add(line2);source.add(line4);source.add(line3);
        return new MemoryTable(meta, source);
    }
}
