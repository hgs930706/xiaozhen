package com.yishang.xiaozhen.util;

/**
 * 封装返回数据
 */
public class ResultUtil<T>{

    private int code;
    private T data;
    private String message;
    public ResultUtil(){

    }

    public ResultUtil(int code, T data, String message){
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public ResultUtil(int code, String message){
        this.code = code;
        this.message = message;
    }


    public static ResultUtil success(){
        return new ResultUtil(0,"success");
    }

    public static ResultUtil error(){
        return new ResultUtil(-1,"fail");
    }

    public static ResultUtil success(Object obj){
        return new ResultUtil(0,obj,"success");
    }

    public static ResultUtil error(String message){
        return new ResultUtil(-1,message);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
