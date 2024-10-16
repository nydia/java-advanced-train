package com.nydia.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tbl_user")
public class User extends BaseEntity implements Serializable {

    private final static long serialVersionUID = 1L;

    @TableField(value = "username")
    public String username;

    @TableField(value = "password")
    public String password;


}
