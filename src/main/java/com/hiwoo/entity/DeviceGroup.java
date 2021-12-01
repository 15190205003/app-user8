package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deviceGroup")
public class DeviceGroup {

    @Id
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "roleId", updatable = false)
    private Integer roleId;

    @Column(name = "name")
    private String name;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "status", updatable = false)
    private Integer status;

    @Column(name = "groupTag")
    private String groupTag;

    @Column(name = "expireTime")
    private Integer expireTime;

    @Column(name = "dataTotalCount")
    private Long dataTotalCount;

    @Column(name = "currTotalCount")
    private Long currTotalCount;

    @Column(name = "isSetExpireTime")
    private Integer isSetExpireTime;

    @Column(name = "isDataOverflow")
    private Integer isDataOverflow;

    @Column(name = "appId")
    private String appId;

    @Column(name = "authGroupId")
    private String authGroupId;

    @Column(name = "tag")
    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public Long getDataTotalCount() {
        return dataTotalCount;
    }

    public void setDataTotalCount(Long dataTotalCount) {
        this.dataTotalCount = dataTotalCount;
    }

    public Long getCurrTotalCount() {
        return currTotalCount;
    }

    public void setCurrTotalCount(Long currTotalCount) {
        this.currTotalCount = currTotalCount;
    }

    public Integer getIsSetExpireTime() {
        return isSetExpireTime;
    }

    public void setIsSetExpireTime(Integer isSetExpireTime) {
        this.isSetExpireTime = isSetExpireTime;
    }

    public Integer getIsDataOverflow() {
        return isDataOverflow;
    }

    public void setIsDataOverflow(Integer isDataOverflow) {
        this.isDataOverflow = isDataOverflow;
    }

    public String getAuthGroupId() {
        return authGroupId;
    }

    public void setAuthGroupId(String authGroupId) {
        this.authGroupId = authGroupId;
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

    public DeviceGroup() {
    }

    public DeviceGroup(String groupTag) {
        this.groupTag = groupTag;
    }

    public DeviceGroup(Integer roleId, String appId) {
        this.roleId = roleId;
        this.appId = appId;
    }

    public DeviceGroup(Integer roleId, Integer pid, String appId) {
        this.roleId = roleId;
        this.pid = pid;
        this.appId = appId;
    }
}
