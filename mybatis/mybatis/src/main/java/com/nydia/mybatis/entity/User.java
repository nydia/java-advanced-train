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
public class User {

    private Integer id;
    private String username;
    private String password;
    private User parent;
    private String desc1;
    private String desc2;

    public User(Integer id){
        this.id = id;
    }

    @Override
    public String toString() {

        String parentStr = "";
        if(this.parent != null){
            parentStr = "User{" +
                    "id='" + id + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", parent='" + password + '\'' +
                    '}';
        }

        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", parent='" + parentStr + '\'' +
                '}';
    }


}
