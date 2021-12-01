package com.hiwoo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


public class OperationLog {

    private Integer type;

    private String operationer;

    private Integer operation_mode;

    private String visit_address;

    private String address;

    private String operation_action;

    private Date createTime;

    private String appId;

    private String authGroupId;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperationer() {
        return operationer;
    }

    public void setOperationer(String operationer) {
        this.operationer = operationer;
    }

    public Integer getOperation_mode() {
        return operation_mode;
    }

    public void setOperation_mode(Integer operation_mode) {
        this.operation_mode = operation_mode;
    }

    public String getVisit_address() {
        return visit_address;
    }

    public void setVisit_address(String visit_address) {
        this.visit_address = visit_address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperation_action() {
        return operation_action;
    }

    public void setOperation_action(String operation_action) {
        this.operation_action = operation_action;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
