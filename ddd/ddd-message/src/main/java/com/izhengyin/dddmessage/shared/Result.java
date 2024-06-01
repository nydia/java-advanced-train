package com.izhengyin.dddmessage.shared;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Result<T> {
    private Error error;
    private T data;

    public boolean isSuccess(){
        return error == null;
    }

    public static <T> Result<T> success(){
        return new Result<>();
    }

    public static <T> Result<T> create(T data){
        return new Result<>(null,data);
    }

    public static <T> Result<T> create(Error error){
        return new Result<>(error,null);
    }

    public static <T> Result<T> create(Error error,T data){
        return new Result<>(error,data);
    }
}