package com.wzy.studyprojectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestBean<T> {

    private Integer status;
    private boolean success;
    private T message;


    public static <T> RestBean<T> success(){
        return new RestBean<>(200,true,null);
    }

    public static <T> RestBean<T> success(T data){
        return new RestBean<>(200,true, data);
    }

    public static <T> RestBean<T> failure(Integer status){
        return new RestBean<>(status,false,null);
    }

    public static <T> RestBean<T> failure(Integer status, T data){
        return new RestBean<>(status,false, data);
    }
}
