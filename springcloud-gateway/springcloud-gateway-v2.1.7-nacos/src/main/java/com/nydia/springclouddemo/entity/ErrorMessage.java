package com.nydia.springclouddemo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: hqlv
 * @Date: 2022/9/28 23:04
 * @Description:
 */
@Setter
@Getter
@Builder
public class ErrorMessage {

    private String message;

    private Integer status;

}
