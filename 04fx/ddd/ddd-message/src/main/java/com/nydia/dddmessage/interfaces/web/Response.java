package com.nydia.dddmessage.interfaces.web;

import lombok.Builder;
import lombok.Data;

/**
 * @author nydia
 * Created on 2021/7/22
 */
@Data
@Builder
public class Response<T> {
    Status status;
    String msg;
    T data;

    public static <T> Response<T> ok() {
        return Response.<T>builder().status(Status.SUCCESS).build();
    }

    public static <T> Response<T> ok(T data) {
        return Response.<T>builder().status(Status.SUCCESS).data(data).build();
    }

    public static <T> Response<T> failed(String msg) {
        return Response.<T>builder().status(Status.FAILED).msg(msg).build();
    }

    public enum Status {
        SUCCESS, FAILED
    }
}