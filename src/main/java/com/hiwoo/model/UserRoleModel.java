package com.hiwoo.model;

import com.hiwoo.entity.MenuInfo;
import com.hiwoo.entity.RoleJurisdiction;

import java.util.List;

public class UserRoleModel {

    private String groupConfigJson;

    private String dataConfigJson;

    private String webcamConfigJson;

    private List<MenuInfo> menuChineseModels;

    private List<MenuInfo> menuEnglishModels;

    private RoleJurisdiction roleJurisdiction;

    public RoleJurisdiction getRoleJurisdiction() {
        return roleJurisdiction;
    }

    public void setRoleJurisdiction(RoleJurisdiction roleJurisdiction) {
        this.roleJurisdiction = roleJurisdiction;
    }


    public String getGroupConfigJson() {
        return groupConfigJson;
    }

    public void setGroupConfigJson(String groupConfigJson) {
        this.groupConfigJson = groupConfigJson;
    }

    public String getDataConfigJson() {
        return dataConfigJson;
    }

    public void setDataConfigJson(String dataConfigJson) {
        this.dataConfigJson = dataConfigJson;
    }

    public String getWebcamConfigJson() {
        return webcamConfigJson;
    }

    public void setWebcamConfigJson(String webcamConfigJson) {
        this.webcamConfigJson = webcamConfigJson;
    }

    public List<MenuInfo> getMenuChineseModels() {
        return menuChineseModels;
    }

    public void setMenuChineseModels(List<MenuInfo> menuChineseModels) {
        this.menuChineseModels = menuChineseModels;
    }

    public List<MenuInfo> getMenuEnglishModels() {
        return menuEnglishModels;
    }

    public void setMenuEnglishModels(List<MenuInfo> menuEnglishModels) {
        this.menuEnglishModels = menuEnglishModels;
    }
}
