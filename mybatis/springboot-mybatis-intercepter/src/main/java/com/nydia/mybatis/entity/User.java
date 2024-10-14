package com.nydia.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity implements Serializable {

    private final static long serialVersionUID = 1L;
    private String username;
    private String password;
    private User parent;
    private Map parentMap;
    private List<User> parentList;
    private List<User> sonList;
    private List<User> parentArrayList;
    private Set<User> parentSet;
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
                    ", username='" + parent.getUsername() + '\'' +
                    ", password='" + parent.getPassword() + '\'' +
                    ", parent='" + parent + '\'' +
                    '}';
        }

        String parentSetStr = "";
        if(this.parentSet != null){
            parentSetStr = "User{" +
                    "id='" + id + '\'' +
                    ", username='" + parentSet.stream().findFirst().get().getUsername()+ '\'' +
                    ", password='" + parentSet.stream().findFirst().get().getPassword() + '\'' +
                    '}';
        }

        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", parent='" + parentStr + '\'' +
                ", parentSetStr='" + parentSetStr + '\'' +
                '}';
    }


}
