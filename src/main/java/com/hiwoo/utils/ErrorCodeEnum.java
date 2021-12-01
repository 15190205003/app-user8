package com.hiwoo.utils;

public enum ErrorCodeEnum {
    ACCOUNT_IS_EMPTY(1001, "认证用户名或密码不能为空"),
    ACCOUNT_IS_NOTEXIST(1002, "认证用户不存在"),
    PASSWORD_INVALID(1003, "认证用户密码不正确"),

    AUTHCODE_INVALID(1004,"无效授权码"),
    AUTGCODE_EXPIRE(1005,"授权码已过期"),
    ACCOUNT_IS_EXPERIENCE(2000,"当前为体验账号");
    private String msg;
    private int code;

     ErrorCodeEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public static String getMsg(int code) {
        for (ErrorCodeEnum c : ErrorCodeEnum.values()) {
            if (c.getCode() == code) {
                return c.msg;
            }
        }
        return null;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
