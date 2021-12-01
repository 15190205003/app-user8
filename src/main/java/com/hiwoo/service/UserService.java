package com.hiwoo.service;

import com.hiwoo.entity.User;
import com.hiwoo.model.UserInfo;
import com.hiwoo.utils.PageResult;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;


public interface UserService extends BaseMapper<User> {

    void updateUserInfo(User user);

    User getUserInfo(int userId);
    PageResult<User> searchUserByPage(String queryName,String tag,String appId,int pageNumber, int pageSize);
    List<User> searchUserList(String queryName, String tag, String appId);

    String getNewPassword(User user);
    void changePassword(String account, String newPassword);
    User getByAccount(String account);
    Integer getUserCountByAppId(String appId,Integer deleteTag);
    void setLocked(int id,Integer locked);

    UserInfo getUserByDevops(String account,String password);
    UserInfo getUserDevopsByUserId(Integer userId);
    UserInfo checkAuthInfo(String appId, String appSecret);
    boolean getUserByAccount(String account);

    Boolean getAuthenticationCache(String account, String pwd);
    void setAuthenticationCache(String account, String pwd);
    void clearAuthenticationCache();

    int deleteUserById(User user);

}
