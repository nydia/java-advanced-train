package com.nydia.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@TableName("tbl_user_book")
public class UserBook extends BaseEntity {

    @TableField("user_id")
    private String userId;

    @TableField("book_name")
    private String bookName;

}
