package com.hiwoo.shiro.session;

import java.io.Serializable;

/**
 *

 * <p>
 *
 * Session 状态 VO
 *
 * <p>
 *
 *
 */
public class SessionStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    //是否踢出 true:有效，false：踢出。
    private Boolean onlineStatus = Boolean.TRUE;


    public Boolean isOnlineStatus(){
        return onlineStatus;
    }

    public Boolean getOnlineStatus() {
        return onlineStatus;
    }
    public void setOnlineStatus(Boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }


}

