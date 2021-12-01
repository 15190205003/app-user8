package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "creator", updatable=false)
    private Integer creator;

    @Column(name = "groupId")
    private Integer groupId;

    @Column(name = "appId")
    private String appId;

    @Column(name = "deviceName")
    private String deviceName;

    @Column(name = "deviceCode")
    private String deviceCode;

    @Column(name = "deviceAddrType")
    private Integer deviceAddrType;

    @Column(name = "deviceAddr")
    private String deviceAddr;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "deviceLogo")
    private String deviceLogo;

    @Column(name = "simpleImg")
    private String simpleImg;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private Integer status;

    @Column(name = "tag")
    private String tag;

    @Column(name = "webcamConfig")
    private String webcamConfig;

    @Column(name = "createTime")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Integer getDeviceAddrType() {
        return deviceAddrType;
    }

    public void setDeviceAddrType(Integer deviceAddrType) {
        this.deviceAddrType = deviceAddrType;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDeviceLogo() {
        return deviceLogo;
    }

    public void setDeviceLogo(String deviceLogo) {
        this.deviceLogo = deviceLogo;
    }

    public String getSimpleImg() {
        return simpleImg;
    }

    public void setSimpleImg(String simpleImg) {
        this.simpleImg = simpleImg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getWebcamConfig() {
        return webcamConfig;
    }

    public void setWebcamConfig(String webcamConfig) {
        this.webcamConfig = webcamConfig;
    }

    public Device() {
    }

    public Device(Integer groupId) {
        this.groupId = groupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
