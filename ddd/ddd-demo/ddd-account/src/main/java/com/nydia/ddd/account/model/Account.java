package com.nydia.ddd.account.model;

import com.nydia.ddd.base.model.BaseEntity;
import lombok.Data;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/3 01:28
 * @Description: Account
 */
@Data
public class Account extends BaseEntity {

    private String id;
    private String username;
    private String password;
    private String name;

}
