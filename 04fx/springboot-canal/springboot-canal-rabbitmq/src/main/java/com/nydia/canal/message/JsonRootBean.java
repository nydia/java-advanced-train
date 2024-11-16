/**
 * Copyright 2024 json.cn
 */
package com.nydia.canal.message;

/**
 * Auto-generated: 2024-11-16 21:48:13
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/
 */
public class JsonRootBean {

    private String data;
    private String database;
    private long es;
    private String gtid;
    private int id;
    private boolean isDdl;
    private String mysqlType;
    private String old;
    private String pkNames;
    private String sql;
    private String sqlType;
    private String table;
    private long ts;
    private String type;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    public void setEs(long es) {
        this.es = es;
    }

    public long getEs() {
        return es;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    public String getGtid() {
        return gtid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIsDdl(boolean isDdl) {
        this.isDdl = isDdl;
    }

    public boolean getIsDdl() {
        return isDdl;
    }

    public void setMysqlType(String mysqlType) {
        this.mysqlType = mysqlType;
    }

    public String getMysqlType() {
        return mysqlType;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getOld() {
        return old;
    }

    public void setPkNames(String pkNames) {
        this.pkNames = pkNames;
    }

    public String getPkNames() {
        return pkNames;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getTs() {
        return ts;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}