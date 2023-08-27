package com.nydia.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserBook {

    private Integer id;
    private User user;
    private String bookName;

    public UserBook(Integer id) {
        this.id = id;
    }

}
