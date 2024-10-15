package com.nydia.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/10/14 22:14
 * @Description: BaseEntity
 */
public class BaseEntity {

    @TableField(value = "id")
    protected Integer id;
    @TableField(value = "create_time")
    protected Date createTime;

}
