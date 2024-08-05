package com.nydia.memorydb.calcite;

import lombok.SneakyThrows;
import org.h2.tools.SimpleResultSet;

import java.util.Map;

public class SimpleResultSetExample {

    @SneakyThrows
    public static void main(String[] args) {
        SimpleResultSet result = new SimpleResultSet();

        // 假设 addRow 接受一个 Map<String, Object> 参数
        // 其中键是列名，值是列的值
        result.addRow(Map.of("column1", "value1", "column2", 123));

        // 或者，如果有更具体的 addRow 方法，例如分别接受每列的值
        result.addRow("value1", 123);

        // 然后你可以遍历结果集，就像普通的 ResultSet 一样
        while (result.next()) {
            System.out.println(result.getString("column1"));
            System.out.println(result.getInt("column2"));
        }
    }

}