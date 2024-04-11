package com.nydia.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.nydia.mybatisplus.enums.SexEnum;
import com.nydia.mybatisplus.typehandle.ListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "`user`",
        //schema = "dev", //数据库的当前用户，比如oracle的用户
        keepGlobalPrefix=false,
        excludeProperty= {"age"},//需要排除的属性名 @since 3.3.1
        autoResultMap = true // autoResultMap配合typeHandler使用才有意义
)
//@KeySequence(value = "SEQ_SYS_USER_ID", dbType = com.baomidou.mybatisplus.annotation.DbType.ORACLE) // 仅支持oracle
public class User {
    //@OrderBy(asc = false, sort = 2)
    @OrderBy(asc = true, sort = 2)
    @TableId(value = "id", type= IdType.ASSIGN_ID)
    //@TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField(value = "name", condition = SqlCondition.LIKE)
    private String name;

    // age这个字段已经被 excludeProperty 排除了
    @TableField("age")
    private Integer age;

    //FieldStrategy.NOT_NULL不允许插入空值
    @TableField(value = "email", insertStrategy = FieldStrategy.DEFAULT, updateStrategy = FieldStrategy.NOT_NULL)
    private String email;

    @TableField(value = "org_ids", typeHandler = ListTypeHandler.class, jdbcType = JdbcType.VARCHAR)
    private List<String> orgIds;

    //update更新的默认值，select：是否查询此字段
    @TableField(value = "update_time", update = "CURRENT_TIMESTAMP()", select = true)
    private LocalDateTime updateTime;

    //fill配合MetaObjectHandler使用，插入或者删除填充字段
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    //更新sql示例： UPDATE `user` SET name=?, email=?, org_ids=?, update_time=CURRENT_TIMESTAMP(), create_by=?, version=?, amount=? WHERE id=? AND version=?
    //更新完成之后 version会增加1
    @Version
    @TableField(value = "version")
    //@TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

    //numericScale这个属性不能用，mybatis底层不支持，但是官方的文档里面为什么不删除，不知道为什么？
    @TableField(value = "amount", numericScale = "2")
    private BigDecimal amount;

    // 主要是解决对数据库中的关键字标志进行替换
    //源码： column = String.format(columnFormat, column);
    @TableField(value = "interval", keepGlobalFormat = true)
    private String interval;

    @TableField(value = "sex")
    private SexEnum sex;

    @TableLogic(value="0",delval="1")
    @TableField(value = "del_f")
    private String delF;

    // *********************************
    // 数据库表中不存在以下字段(表join时会用到)
    // *********************************

    @TableField(value = "is_new", exist = false)
    private Boolean isNew;//是否新增
}