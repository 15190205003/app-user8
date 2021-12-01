package com.hiwoo.model;

import com.hiwoo.entity.*;

import java.util.List;

public class UserRoleAuthModel {

    private UserInfo userInfo;

    private RoleJurisdiction roleJurisdiction;

    private List<MenuInfo> menuChineses;

    private List<MenuInfo> menuEnglishes;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public RoleJurisdiction getRoleJurisdiction() {
        return roleJurisdiction;
    }

    public void setRoleJurisdiction(RoleJurisdiction roleJurisdiction) {
        this.roleJurisdiction = roleJurisdiction;
    }

    public List<MenuInfo> getMenuChineses() {
        return menuChineses;
    }

    public void setMenuChineses(List<MenuInfo> menuChineses) {
        this.menuChineses = menuChineses;
    }

    public List<MenuInfo> getMenuEnglishes() {
        return menuEnglishes;
    }

    public void setMenuEnglishes(List<MenuInfo> menuEnglishes) {
        this.menuEnglishes = menuEnglishes;
    }

}
