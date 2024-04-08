package com.nydia.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.nydia.mybatisplus.typehandle.ListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

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
public class User {
    @TableId(type= IdType.ASSIGN_ID)
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
    @TableField(value = "update_time", update = "CURRENT_TIMESTAMP()", select = true, keepGlobalFormat = false)
    private LocalDateTime updateTime;
    //fill配合MetaObjectHandler使用，插入或者删除填充字段
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

    // *********************************
    // 数据库表中不存在以下字段(表join时会用到)
    // *********************************

    @TableField(value = "is_new", exist = false)
    private Boolean isNew;//是否新增
}