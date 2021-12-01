package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu_role")
public class MenuRole {

    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "roleId")
    private Integer roleId;

    @Column(name = "readStatus")
    private Integer readStatus;

    @Column(name = "editStatus")
    private Integer editStatus;

    @Column(name = "deleteStatus")
    private Integer deleteStatus;

    @Column(name = "mid")
    private Integer mid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    public MenuRole() {
    }

    public MenuRole(Integer roleId) {
        this.roleId = roleId;
    }

    public MenuRole(Integer roleId, Integer mid) {
        this.roleId = roleId;
        this.mid = mid;
    }
}
