package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "roleJurisdiction")
public class RoleJurisdiction {

    @Id
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "roleDescribe")
    private String roleDescribe;

    @Column(name = "configJson")
    private String configJson;

    @Column(name = "alertMsg")
    private Integer alertMsg;

    @Column(name = "isAdmin")
    private Integer isAdmin;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "appId")
    private String appId;

    @Column(name = "tag")
    private String tag;

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

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
