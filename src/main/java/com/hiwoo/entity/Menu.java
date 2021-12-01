package com.hiwoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "guideChinese")
    private String guideChinese;

    @Column(name = "guideEnglish")
    private String guideEnglish;

    @Column(name = "i")
    private String i;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "path")
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
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


}
