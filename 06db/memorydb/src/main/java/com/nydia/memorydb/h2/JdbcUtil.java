package com.nydia.memorydb.h2;

import cn.hutool.core.date.StopWatch;
import com.nydia.memorydb.base.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lvhq
 * @date 2024.07.03
 */
@Slf4j
public class JdbcUtil {
    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 获取 NamedParameterJdbcTemplate,用于：批量插入，批量更新，查询 等操作
     *
     * @return NamedParameterJdbcTemplate
     */
    public static NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        synchronized (JdbcUtil.class) {
            if (namedParameterJdbcTemplate == null) {
                DataSource dataSource = SpringContextUtils.getBean("h2DataSource", DataSource.class);
                namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
                return namedParameterJdbcTemplate;
            }
        }
        return namedParameterJdbcTemplate;
    }

    /**
     * 获取 JdbcTemplate,用于： 创建表，删除表，删除函数，创建函数 等操作
     *
     * @return JdbcTemplate
     */
    public static JdbcTemplate jdbcTemplate() {
        return namedParameterJdbcTemplate().getJdbcTemplate();
    }

    /**
     * 删除表
     *
     * @param sql 执行语句
     */
    public static void dropTable(String sql) {
        StopWatch sw = new StopWatch(sql);
        sw.start();
        jdbcTemplate().execute(sql);
        sw.stop();
        log.info(sw.shortSummary(TimeUnit.MILLISECONDS));
    }

    /**
     * 删除函数
     *
     * @param sql 执行语句
     */
    public static void dropFunc(String sql) {
        jdbcTemplate().execute(sql);
    }

    /**
     * 创建表
     *
     * @param sql 执行语句
     */
    public static void createTable(String sql) {
        StopWatch sw = new StopWatch(sql);
        sw.start();
        jdbcTemplate().execute(sql);
        sw.stop();
        log.info(sw.shortSummary(TimeUnit.MILLISECONDS));
    }

    /**
     * 创建函数
     *
     * @param sql 执行语句
     */
    public static void createFunc(String sql) {
        jdbcTemplate().execute(sql);
    }

    /**
     * 批量插入数据
     *
     * @param sql         执行语句
     * @param batchValues 批量值
     */
    public static int[] batchInsert(String sql, Map<String, Object>[] batchValues) {
        StopWatch sw = new StopWatch(sql);
        try {
            sw.start();
            return namedParameterJdbcTemplate().batchUpdate(sql, batchValues);
        } finally {
            sw.stop();
            log.info(sw.shortSummary(TimeUnit.MILLISECONDS));
        }
    }

    /**
     * 删除数据
     *
     * @param sql      执行语句
     * @param paramMap 参数值
     */
    public static int batchDelete(String sql, Map<String, Object> paramMap) {
        return namedParameterJdbcTemplate().update(sql, paramMap);
    }

    /**
     * 查询Map列表
     *
     * @param sql  执行语句
     * @param args 参数数组
     */
    public static List<Map<String, Object>> queryList(String sql, Object[] args) {
        StopWatch sw = new StopWatch(sql);
        sw.start();
        List<Map<String, Object>> list = jdbcTemplate().queryForList(sql, args);
        sw.stop();
        log.info(sw.shortSummary(TimeUnit.MILLISECONDS));
        return list;
    }

    /**
     * 查询指定对象列表
     *
     * @param sql       执行语句
     * @param args      参数数组
     * @param rowMapper 结果处理
     */
    public static <T> List<T> queryList(String sql, Object[] args, RowMapper<T> rowMapper) {
        return jdbcTemplate().query(sql, rowMapper, args);
    }

    /**
     * 对象查询
     *
     * @param sql    执行语句
     * @param args   参数数组
     * @param tClass 返回类型
     * @param <T>    泛型
     * @return 返回值
     */
    public static <T> T queryForObject(String sql, Object[] args, Class<T> tClass) {
        StopWatch sw = new StopWatch(sql);
        sw.start();
        T t = jdbcTemplate().queryForObject(sql, tClass, args);
        sw.stop();
        log.info(sw.shortSummary(TimeUnit.MILLISECONDS));
        return t;
    }

}
