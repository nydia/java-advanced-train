package com.nydia.mybatis.test.annatations;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * @Description Provider Test
 * @Date 2023/9/11 15:37
 * @Created by nydia
 */
public class ProviderTest {

    @Test
    public void selectProvider() throws NoSuchMethodException{
        Class<?> mapperType = DefaultSqlProviderMapper.class;
        Configuration configuration = new Configuration();
        configuration.setDefaultSqlProviderType(DefaultSqlProviderMapper.SqlProvider.class);

        Method mapperMethod = mapperType.getMethod("select", int.class);
        String sql = new ProviderSqlSource(configuration, mapperMethod.getAnnotation(SelectProvider.class), mapperType,
                mapperMethod).getBoundSql(1).getSql();
        assertEquals("select name from foo where id = ?", sql);
    }

    @Test
    public void selectProvider2() throws NoSuchMethodException{
        Class<?> mapperType = DefaultSqlProviderMapper.class;
        Configuration configuration = new Configuration();
        configuration.setDefaultSqlProviderType(DefaultSqlProviderMapper.SqlProvider.class);

        Method mapperMethod = mapperType.getMethod("select2", int.class);
        String sql = new ProviderSqlSource(configuration, mapperMethod.getAnnotation(SelectProvider.class), mapperType,
                mapperMethod).getBoundSql(1).getSql();
        assertEquals("select name from foo where id = ?", sql);
    }

    public interface DefaultSqlProviderMapper {

        @SelectProvider
        String select(int id);

        @SelectProvider
        String select2(int id);

        @InsertProvider
        void insert(String name);

        @UpdateProvider
        void update(int id, String name);

        @DeleteProvider
        void delete(int id);

        class SqlProvider {

            public static String provideSql(ProviderContext c) {
                switch (c.getMapperMethod().getName()) {
                    case "select":
                        return "select name from foo where id = #{id}";
                    case "select2":
                        return new SQL().SELECT("*").FROM("foo").WHERE("id = #{id}").toString();
                    case "insert":
                        return "insert into foo (name) values(#{name})";
                    case "update":
                        return "update foo set name = #{name} where id = #{id}";
                    default:
                        return "delete from foo where id = #{id}";
                }
            }

            private SqlProvider() {
            }

        }

    }


}
