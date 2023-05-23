package com.nydia.modules.entity.master;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Integer userId;
    private String userName;
    private String password;
    private String nickName;
    private String idCard;

}
