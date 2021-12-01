package com.hiwoo.model;

public class UserLoginInfoModel {

    private UserInfo userInfo;

    private UserRoleModel userRoleModel;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserRoleModel getUserRoleModel() {
        return userRoleModel;
    }

    public void setUserRoleModel(UserRoleModel userRoleModel) {
        this.userRoleModel = userRoleModel;
    }

    public UserLoginInfoModel(UserInfo userInfo, UserRoleModel userRoleModel) {
        this.userInfo = userInfo;
        this.userRoleModel = userRoleModel;
    }

    public UserLoginInfoModel() {

    }
}
