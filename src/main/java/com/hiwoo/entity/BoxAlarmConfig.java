package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 盒子报警配置完整信息
 */
@Entity
@Table(name = "boxAlarmConfig")
public class BoxAlarmConfig {

    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "ruleName")
    private String ruleName;

    @Column(name = "ruleCode")
    private String ruleCode;

    @Column(name = "conditionMsg")
    private String condition;

    @Column(name = "action")
    private String action;

    @Column(name = "content")
    private String content;

    @Column(name = "alarmVersion")
    private String alarmVersion;

    @Column(name = "appId")
    private String appId;

    @Column(name = "authGroupId")
    private String authGroupId;

    @Column(name = "clearAlarm")
    private Integer clearAlarm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAlarmVersion() {
        return alarmVersion;
    }

    public void setAlarmVersion(String alarmVersion) {
        this.alarmVersion = alarmVersion;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAuthGroupId() {
        return authGroupId;
    }

    public void setAuthGroupId(String authGroupId) {
        this.authGroupId = authGroupId;
    }

    public Integer getClearAlarm() {
        return clearAlarm;
    }

    public void setClearAlarm(Integer clearAlarm) {
        this.clearAlarm = clearAlarm;
    }

    public BoxAlarmConfig() {
    }

    public BoxAlarmConfig(String ruleCode) {
        this.ruleCode = ruleCode;
    }

}
