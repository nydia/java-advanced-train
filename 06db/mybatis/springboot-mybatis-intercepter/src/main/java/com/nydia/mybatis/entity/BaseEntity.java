package com.nydia.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/10/14 22:14
 * @Description: BaseEntity
 */
public class BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)    //主键
    public String id;

    @TableField(value = "create_time")
    public Date createTime;

    @TableField(value = "create_by")
    public String createBy;

}
