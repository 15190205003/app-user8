package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 设备下报警配置信息
 */
@Entity
@Table(name = "deviceAlarmConfig")
public class DeviceAlarmConfig {

    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "deviceId", updatable=false)
    private Integer deviceId;

    @Column(name = "ruleCode")
    private String ruleCode;

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

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public DeviceAlarmConfig() {
    }

    public DeviceAlarmConfig(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceAlarmConfig(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public DeviceAlarmConfig(Integer deviceId, String ruleCode) {
        this.deviceId = deviceId;
        this.ruleCode = ruleCode;
    }
}
