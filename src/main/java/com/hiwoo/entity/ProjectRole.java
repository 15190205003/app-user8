package com.hiwoo.entity;

import java.util.List;

public class ProjectRole {

    private Integer isAuthor;

    private Integer projectId;

    private String projectName;

    private Integer dashboard;

    private Integer analysis;

    private Integer scadaManager;

    private Integer scadaTemplate;

    private List<DeviceRole> deviceList;

    public Integer getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(Integer isAuthor) {
        this.isAuthor = isAuthor;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getDashboard() {
        return dashboard;
    }

    public void setDashboard(Integer dashboard) {
        this.dashboard = dashboard;
    }

    public Integer getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Integer analysis) {
        this.analysis = analysis;
    }

    public List<DeviceRole> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceRole> deviceList) {
        this.deviceList = deviceList;
    }

    public Integer getScadaManager() {
        return scadaManager;
    }

    public void setScadaManager(Integer scadaManager) {
        this.scadaManager = scadaManager;
    }

    public Integer getScadaTemplate() {
        return scadaTemplate;
    }

    public void setScadaTemplate(Integer scadaTemplate) {
        this.scadaTemplate = scadaTemplate;
    }
}
