package com.wu.crm.commons.domain;

public class ReturnObject {
    private String code;  //判断是否登陆成功
    private String message;  //登陆失败的问题
    private Object other;  //其他返回数据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

}
