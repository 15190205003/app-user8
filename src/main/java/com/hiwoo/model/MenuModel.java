package com.hiwoo.model;


import java.util.List;

public class MenuModel {

    private String guideChinese;

    private String guideEnglish;

    private String path;

    private String i;

    private Integer id;

    private Integer pid;

    private Integer readStatus;

    private Integer editStatus;

    private Integer deleteStatus;

    private Integer mid;

    private List<MenuModel> children;

    public String getGuideChinese() {
        return guideChinese;
    }

    public void setGuideChinese(String guideChinese) {
        this.guideChinese = guideChinese;
    }

    public String getGuideEnglish() {
        return guideEnglish;
    }

    public void setGuideEnglish(String guideEnglish) {
        this.guideEnglish = guideEnglish;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public List<MenuModel> getChildren() {
        return children;
    }

    public void setChildren(List<MenuModel> children) {
        this.children = children;
    }
}
