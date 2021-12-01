package com.hiwoo.license;

public class LicenseCheckModel {

    private static final long serialVersionUID = 1L;

    /**
     * 可被允许的操作系统名称
     */
    private  String oSName;

    /**
     * 可被允许的MAC地址
     */
    private String macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

    /**
     * 申请日期
     */
    private String issuedDate;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOSName() {
        return oSName;
    }

    public void setOSName(String osName) {
        this.oSName = osName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getCpuSerial() {
        return cpuSerial;
    }

    public void setCpuSerial(String cpuSerial) {
        this.cpuSerial = cpuSerial;
    }

    public String getMainBoardSerial() {
        return mainBoardSerial;
    }

    public void setMainBoardSerial(String mainBoardSerial) {
        this.mainBoardSerial = mainBoardSerial;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }
}
