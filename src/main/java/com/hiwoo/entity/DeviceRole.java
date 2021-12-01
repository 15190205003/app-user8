package com.hiwoo.entity;

import java.util.List;

public class DeviceRole {

    private Integer projectId;

    private String projectName;

    private Integer deviceId;

    private String deviceName;

    private Integer readStatus;

    private Integer editStatus;

    private Integer deleteStatus;

    /*    以上設備管理權限*/

    private Integer dataStatus;

    private Integer alarmStatus;

    private Integer eventStatus;

    private Integer scadaStatus;

    private Integer webcamStatus;

    private Integer mapStatus;

    private List<DataRule> dataList;

    private List<AlarmEventRule> alarmList;

    private List<AlarmEventRule> evnetList;

    private List<WebcamRule> webcamList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Integer getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(Integer alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public Integer getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(Integer eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Integer getScadaStatus() {
        return scadaStatus;
    }

    public void setScadaStatus(Integer scadaStatus) {
        this.scadaStatus = scadaStatus;
    }

    public Integer getWebcamStatus() {
        return webcamStatus;
    }

    public void setWebcamStatus(Integer webcamStatus) {
        this.webcamStatus = webcamStatus;
    }

    public Integer getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(Integer mapStatus) {
        this.mapStatus = mapStatus;
    }

    public List<DataRule> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataRule> dataList) {
        this.dataList = dataList;
    }

    public List<AlarmEventRule> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<AlarmEventRule> alarmList) {
        this.alarmList = alarmList;
    }

    public List<AlarmEventRule> getEvnetList() {
        return evnetList;
    }

    public void setEvnetList(List<AlarmEventRule> evnetList) {
        this.evnetList = evnetList;
    }

    public List<WebcamRule> getWebcamList() {
        return webcamList;
    }

    public void setWebcamList(List<WebcamRule> webcamList) {
        this.webcamList = webcamList;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
