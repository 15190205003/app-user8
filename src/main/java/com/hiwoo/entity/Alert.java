package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alert")
public class Alert {
    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "message")
    private Integer message;

    @Column(name = "email")
    private Integer email;

    @Column(name = "wechat")
    private Integer wechat;

    @Column(name = "role")
    private Integer role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getEmail() {
        return email;
    }

    public void setEmail(Integer email) {
        this.email = email;
    }

    public Integer getWechat() {
        return wechat;
    }

    public void setWechat(Integer wechat) {
        this.wechat = wechat;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Alert() {
    }

    public Alert(Integer role) {
        this.role = role;
    }
}
