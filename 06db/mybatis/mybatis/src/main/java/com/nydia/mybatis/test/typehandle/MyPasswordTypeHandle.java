package com.nydia.mybatis.test.typehandle;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/8/13 16:26
 * @Description: MyPasswordTypeHandle
 */
@MappedJdbcTypes({JdbcType.VARCHAR})  //对应数据库类型
@MappedTypes({String.class})              //java数据类型
public class MyPasswordTypeHandle implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        String value = (parameter == null || parameter.trim() == "") ? "123456" : parameter;
        ps.setString(i, value);
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }
}
