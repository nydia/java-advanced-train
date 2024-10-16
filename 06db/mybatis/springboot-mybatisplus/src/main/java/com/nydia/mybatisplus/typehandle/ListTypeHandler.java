package com.nydia.mybatisplus.typehandle;

import lombok.extern.java.Log;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@MappedJdbcTypes(JdbcType.VARCHAR)  //数据库类型
@MappedTypes({List.class})          //java数据类型
@Log
public class ListTypeHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement ps, int i,
                             List<String> parameter, JdbcType jdbcType) throws SQLException {
        log.info("method ====>>> setParameter");
        String orgIds = dealListToOneStr(parameter);
        ps.setString(i, orgIds);
    }


    /**
     * 集合拼接字符串
     *
     * @param parameter
     * @return
     */
    private String dealListToOneStr(List<String> parameter) {
        if (parameter == null || parameter.size() <= 0)
            return null;
        String res = "";
        for (int i = 0; i < parameter.size(); i++) {
            if (i == parameter.size() - 1) {
                res += parameter.get(i);
                return res;
            }
            res += parameter.get(i) + ",";
        }
        return null;
    }


    @Override
    public List<String> getResult(ResultSet rs, String columnName)
            throws SQLException {
        log.info("method ====>>> getResult(ResultSet rs, String columnName)");
        return Arrays.asList(rs.getString(columnName).split(","));
    }


    @Override
    public List<String> getResult(ResultSet rs, int columnIndex)
            throws SQLException {
        log.info("method ====>>> getResult(ResultSet rs, int columnIndex)");
        return Arrays.asList(rs.getString(columnIndex).split(","));
    }


    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("method ====>>> getResult(CallableStatement cs, int columnIndex)");
        String orgIds = cs.getString(columnIndex);
        return Arrays.asList(orgIds.split(","));
    }

}