package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "individuation")
public class Individuation {

    @Id
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "userId", updatable = false)
    private Integer userId;

    @Column(name = "companyLogo")
    private String companyLogo;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "systemName")
    private String systemName;

    @Column(name = "appId", updatable = false)
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Individuation() {
    }

    public Individuation(String appId) {
        this.appId = appId;
    }
}
