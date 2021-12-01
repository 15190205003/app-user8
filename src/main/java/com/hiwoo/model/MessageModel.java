package com.hiwoo.model;

public class MessageModel {
    private String account;
    private String pswd;
    private String mobile;
    private String msg;
    private boolean needstatus;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isNeedstatus() {
        return needstatus;
    }

    public void setNeedstatus(boolean needstatus) {
        this.needstatus = needstatus;
    }
}
