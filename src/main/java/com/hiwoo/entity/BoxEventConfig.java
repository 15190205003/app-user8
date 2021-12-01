package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 盒子事件配置完整信息
 */
@Entity
@Table(name = "boxEventConfig")
public class BoxEventConfig {

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

    @Column(name = "eventVersion")
    private String eventVersion;

    @Column(name = "appId")
    private String appId;

    @Column(name = "authGroupId")
    private String authGroupId;

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

    public String getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
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

    public BoxEventConfig() {
    }

    public BoxEventConfig(String ruleCode) {
        this.ruleCode = ruleCode;
    }
}
