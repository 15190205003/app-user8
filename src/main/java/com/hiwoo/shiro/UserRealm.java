package com.hiwoo.shiro;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiwoo.entity.User;
import com.hiwoo.model.UserInfo;
import com.hiwoo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;


public class UserRealm extends AuthorizingRealm {
   
	@Autowired
    private UserService userService;

    @Value("${IsPrivatization}")
    private Integer IsPrivatization;

     public UserRealm()
     {
    	 super();
     }
	

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = (String)principals.getPrimaryPrincipal();
        User user= userService.getByAccount(account);
       
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        
        Set<String> roles=new HashSet<String>();
        roles.add(user.getRole()+"");
        authorizationInfo.setRoles(roles);
        //authorizationInfo.setStringPermissions(userService.findPermissions(username));

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String account = (String)token.getPrincipal();
        char [] chars= (char [])token.getCredentials();
        String password = String.valueOf(chars);
        User user = null;

        if (IsPrivatization == 0) {
            user = userService.getByAccount(account);
            if (null == user) {
                UserInfo userInfo = getUserByPrivatization(account,password);
                if(null != userInfo){
                    user = new User();
                    user.setAccount(userInfo.getAccount());
                    user.setPassword(new SimpleHash(
                            "md5",
                            userInfo.getPassword(),
                            ByteSource.Util.bytes(user.getCredentialsSalt()),
                            2).toHex());
                    user.setUserName(userInfo.getUserName());
                    user.setCreateTime(userInfo.getCreateTime());
                    user.setLocked(0);
                }
            }
        } else if (1 == IsPrivatization) {
            user = userService.getByAccount(account);
        }

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现

        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user.getAccount(), //用户名
              user.getPassword(), //密码
               new MySimpleByteSource(user.getCredentialsSalt()),//salt=username+salt
//             ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
              getName());

        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    public UserInfo getUserByPrivatization(String account,String password) {
            UserInfo userInfo = userService.getUserByDevops(account, password);
            return userInfo;
    }
}
