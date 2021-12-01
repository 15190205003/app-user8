package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 设备权限下盒子配置信息
 */
@Entity
@Table(name = "deviceConfig")
public class DeviceConfig {

    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "deviceId", updatable=false)
    private Integer deviceId;

    @Column(name = "boxId")
    private String boxId;

    @Column(name = "boxName")
    private String boxName;

    @Column(name = "dataConfig")
    private String dataConfig;

    @Column(name = "dataVersion")
    private String dataVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getDataConfig() {
        return dataConfig;
    }

    public void setDataConfig(String dataConfig) {
        this.dataConfig = dataConfig;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public DeviceConfig() {
    }

    public DeviceConfig(String boxId) {
        this.boxId = boxId;
    }

    public DeviceConfig(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceConfig(Integer deviceId, String boxId) {
        this.deviceId = deviceId;
        this.boxId = boxId;
    }
}
