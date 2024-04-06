package com.nydia.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nydia.mybatisplus.typehandle.ListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
    @TableField("name")
    private String name;
    @TableField("age")
    private Integer age;
    @TableField("email")
    private String email;
    @TableField(value = "org_ids", typeHandler = ListTypeHandler.class)
    private List<String> orgIds;

    // *********************************
    // 数据库表中不存在以下字段(表join时会用到)
    // *********************************

    @TableField(value = "is_new", exist = false)
    private Boolean isNew;//是否新增
}