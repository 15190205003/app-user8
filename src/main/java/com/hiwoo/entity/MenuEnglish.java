package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menuEnglish")
public class MenuEnglish {

    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "guide")
    private String guide;

    @Column(name = "i")
    private String i;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "path")
    private String path;

    @Column(name = "role")
    private Integer role;

    @Column(name = "readStatus")
    private Integer readStatus;

    @Column(name = "editStatus")
    private Integer editStatus;

    @Column(name = "deleteStatus")
    private Integer deleteStatus;

    @Column(name = "mid")
    private Integer mid;

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

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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

    public MenuEnglish() {
    }

    public MenuEnglish(Integer role) {
        this.role = role;
    }
}
