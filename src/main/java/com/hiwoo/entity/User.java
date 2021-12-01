package com.hiwoo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "Id", updatable = false)
    private Integer id;

    @Column(name = "account", updatable = false)
    private String account;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "headPortrait")
    private String headPortrait;

    @Column(name = "role")
    private Integer role;

    @Column(name = "locked")
    private Integer locked;

    @Column(name = "creatorId")
    private Integer creatorId;

    @Column(name = "appId")
    private String appId;

    @Column(name = "appSecret")
    private String appSecret;

    @Column(name = "tag")
    private String tag;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "authGroupId")
    private String authGroupId;

    @Column(name = "deleteTag")
    private Integer deleteTag;

    public Integer getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(Integer deleteTag) {
        this.deleteTag = deleteTag;
    }

    public String getAuthGroupId() {
        return authGroupId;
    }

    public void setAuthGroupId(String authGroupId) {
        this.authGroupId = authGroupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User() {
    }

    public User(User user) {
        this.id = user.id;
        this.account = user.account;
        this.userName = user.userName;
        this.password = user.password;
        this.salt = user.salt;
        this.phone = user.phone;
        this.email = user.email;
        this.headPortrait = user.headPortrait;
        this.role = user.role;
        this.locked = user.locked;
        this.creatorId = user.creatorId;
        this.appId = user.appId;
        this.appSecret = user.appSecret;
        this.tag = user.tag;
        this.createTime = user.createTime;
    }

    public String getCredentialsSalt() {
        return account + salt;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User(Integer role, Integer deleteTag) {
        this.role = role;
        this.deleteTag = deleteTag;
    }
}
