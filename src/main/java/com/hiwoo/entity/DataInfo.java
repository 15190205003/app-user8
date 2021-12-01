package com.hiwoo.entity;

public class DataInfo {

    private String dataIndex;

    private String dataName;

    private Integer dataType;

    private Integer operationType; // 0 只读 1 只写 2 读写

    private double dataValue;

    private String dataLogo;

    private String dataUnit;

    private Integer rangeMax;

    private Integer rangeMin;

    private Integer opStatus;     // 0 表示无变化 1 表示添加 2 表示修改 3 表示删除

    private Integer isStorage; // 1 存儲

    private Integer deviceId;

    private String boxId;

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

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getDataLogo() {
        return dataLogo;
    }

    public void setDataLogo(String dataLogo) {
        this.dataLogo = dataLogo;
    }

    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }

    public Integer getRangeMax() {
        return rangeMax;
    }

    public void setRangeMax(Integer rangeMax) {
        this.rangeMax = rangeMax;
    }

    public Integer getRangeMin() {
        return rangeMin;
    }

    public void setRangeMin(Integer rangeMin) {
        this.rangeMin = rangeMin;
    }

    public Integer getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(Integer opStatus) {
        this.opStatus = opStatus;
    }

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public double getDataValue() {
        return dataValue;
    }

    public void setDataValue(double dataValue) {
        this.dataValue = dataValue;
    }

}
