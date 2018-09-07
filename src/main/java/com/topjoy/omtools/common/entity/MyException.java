package com.topjoy.omtools.common.entity;
/**
 * 自定义异常类
 */
public class MyException extends RuntimeException {

    private String code;
    private String msg;
    private Object data;


    public MyException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
