package com.hiwoo.model;


import com.hiwoo.entity.ProjectRole;

import java.util.Date;
import java.util.List;

public class RoleJurisdictionModel {

    private Integer id;

    private Integer userId;

    private String creatorName;

    private String roleName;

    private String roleDescribe;

    private Integer alertMsg;

    private Integer isAdmin;

    private String tag;

    private Date createTime;

    private String appId;

    private List<MenuModel> menuModelList;

    private List<ProjectRole> projectList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public Integer getAlertMsg() {
        return alertMsg;
    }

    public void setAlertMsg(Integer alertMsg) {
        this.alertMsg = alertMsg;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<ProjectRole> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectRole> projectList) {
        this.projectList = projectList;
    }

    public List<MenuModel> getMenuModelList() {
        return menuModelList;
    }

    public void setMenuModelList(List<MenuModel> menuModelList) {
        this.menuModelList = menuModelList;
    }
}
