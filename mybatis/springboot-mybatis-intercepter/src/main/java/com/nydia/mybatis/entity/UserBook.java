package com.nydia.mybatis.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBook extends BaseEntity {

    private User user;
    private String bookName;

    public UserBook(Integer id) {
        this.id = id;
    }

}
