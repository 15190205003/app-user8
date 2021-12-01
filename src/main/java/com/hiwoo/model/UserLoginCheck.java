package com.hiwoo.model;

import org.hibernate.validator.constraints.NotEmpty;

public class UserLoginCheck {

    private int userid;
    private String orgPassword;
    private String newPassword;
    private String confirmPassword;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @NotEmpty(message="原密码不能为空")
    public String getOrgPassword() {
        return orgPassword;
    }

    public void setOrgPassword(String orgPassword) {
        this.orgPassword = orgPassword;
    }

    @NotEmpty(message="新密码不能为空")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotEmpty(message="确认密码不能为空")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
