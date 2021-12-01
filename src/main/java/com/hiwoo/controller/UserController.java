package com.hiwoo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiwoo.entity.*;
import com.hiwoo.model.*;
import com.hiwoo.model.DataAuth;
import com.hiwoo.service.*;
import com.hiwoo.shiro.cache.JedisManager;
import com.hiwoo.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleJurisdictionService roleJurisdictionService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRoleService menuRoleService;

    @Autowired
    private IndividuationService individuationService;

    @Resource
    private JedisManager jedisManager;

    @Value("${IsPrivatization}")
    private Integer IsPrivatization;

    @Value("${experience.user}")
    private String experience;

    @Value("${auth.userCount}")
    private Integer userCount;
    @Value("${auth.dataCount}")
    private Integer dataCount;
    @Value("${auth.msgCount}")
    private Integer msgCount;
    @Value("${auth.webcamCount}")
    private Integer webcamCount;
    @Value("${auth.dataTotalCount}")
    private Integer dataTotalCount;

    @Value("${server.url.system}")
    private String system_url;

    @Value("${application.headPortraitFile}")
    private String headPortrait;

    @Value("${application.baseUrl}")
    private String baseUrl;

    /**
     * 对外服务鉴权
     *
     * @return
     */
    @PostMapping("/getAuthentication")
    public UserRoleAuthModel getAuthentication(@RequestBody ReqParamObject reqParamObject) {
        UserRoleAuthModel userRoleAuthModel = new UserRoleAuthModel();
        UserInfo userInfo = null;
        User user = userService.getUserInfo(reqParamObject.getUserId());
        if (null != user) {
            userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setAccount(user.getAccount());
            userInfo.setUserName(user.getUserName());
            userInfo.setRole(user.getRole());
            userInfo.setTag(user.getTag());
            userInfo.setAppId(user.getAppId());
            userInfo.setAppSecret(user.getAppSecret());
            userInfo.setAuthGroupId(user.getAuthGroupId());
        } else {
            if (0 == IsPrivatization) {
                userInfo = userService.getUserDevopsByUserId(reqParamObject.getUserId());
                List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                if (null != individuations && individuations.size() > 0) {
                    userInfo.setUserName(individuations.get(0).getCompanyName());
                }
            }
        }
        if (null == userInfo) {
            return null;
        } else {
            userRoleAuthModel.setUserInfo(userInfo);
        }
        RoleJurisdiction roleJurisdiction = roleJurisdictionService.selectByPrimaryKey(userInfo.getRole());
        if (null == roleJurisdiction) {
            return null;
        } else {
            roleJurisdiction.setAppId(userRoleAuthModel.getUserInfo().getAppId());
            userRoleAuthModel.setRoleJurisdiction(roleJurisdiction);
        }

        List<MenuRole> menuRoles = menuRoleService.getMenuRoleByRole(roleJurisdiction.getId());
        List<MenuModel> menuModels = null;
        if (null != menuRoles && menuRoles.size() > 0) {
            menuModels = menuService.getMenuList(menuRoles);
        }

        if (null != menuModels && menuModels.size() > 0) {
            List<MenuInfo> menuChineses = new ArrayList<MenuInfo>();
            for (MenuModel menuModel : menuModels) {
                MenuInfo menuInfo = new MenuInfo();
                BeanUtils.copyProperties(menuModel, menuInfo);
                menuInfo.setGuide(menuModel.getGuideChinese());
                menuChineses.add(menuInfo);
            }
            userRoleAuthModel.setMenuChineses(menuChineses);
        }

        if (0 == IsPrivatization) {
            if (null == userRoleAuthModel.getUserInfo().getDataAuth()) {
                UserInfo userInfo1 = userService.checkAuthInfo(userRoleAuthModel.getUserInfo().getAppId(), userRoleAuthModel.getUserInfo().getAppSecret());
                userInfo.setDataAuth(userInfo1.getDataAuth());
            }
        } else {
            userInfo.setDataAuth(getDefaultDataAuth());
        }
        return userRoleAuthModel;
    }

    /**
     * 对外服务鉴权
     *
     * @return
     */
    @PostMapping("/getAuthAndFindInfo")
    public UserRoleAuthModel getAuthAndFindInfo(@RequestBody ReqParamObject reqParamObject) {
        UserRoleAuthModel userRoleAuthModel = new UserRoleAuthModel();
        UserInfo userInfo = null;
        User user = userService.getUserInfo(reqParamObject.getUserId());
        if (null != user) {
            userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setAccount(user.getAccount());
            userInfo.setUserName(user.getUserName());
            userInfo.setRole(user.getRole());
            userInfo.setTag(user.getTag());
            userInfo.setAppId(user.getAppId());
            userInfo.setAppSecret(user.getAppSecret());
            userInfo.setAuthGroupId(user.getAuthGroupId());
        } else {
            if (0 == IsPrivatization) {
                userInfo = userService.getUserDevopsByUserId(reqParamObject.getUserId());
                List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                if (null != individuations && individuations.size() > 0) {
                    userInfo.setUserName(individuations.get(0).getCompanyName());
                }
            }
        }
        if (null == userInfo) {
            return null;
        } else {
            userRoleAuthModel.setUserInfo(userInfo);
        }
        RoleJurisdiction roleJurisdiction = roleJurisdictionService.selectByPrimaryKey(userInfo.getRole());
        if (null == roleJurisdiction) {
            return null;
        } else {
            roleJurisdiction.setAppId(userRoleAuthModel.getUserInfo().getAppId());
            userRoleAuthModel.setRoleJurisdiction(roleJurisdiction);
        }

        List<MenuRole> menuRoles = menuRoleService.getMenuRoleByRole(roleJurisdiction.getId());
        List<MenuModel> menuModels = null;
        if (null != menuRoles && menuRoles.size() > 0) {
            menuModels = menuService.getMenuList(menuRoles);
        }

        if (null != menuModels && menuModels.size() > 0) {
            List<MenuInfo> menuChineses = new ArrayList<MenuInfo>();
            for (MenuModel menuModel : menuModels) {
                MenuInfo menuInfo = new MenuInfo();
                BeanUtils.copyProperties(menuModel, menuInfo);
                menuInfo.setGuide(menuModel.getGuideChinese());
                menuChineses.add(menuInfo);
            }
            userRoleAuthModel.setMenuChineses(menuChineses);
        }

        if (0 == IsPrivatization) {
            if (null == userRoleAuthModel.getUserInfo().getDataAuth()) {
                UserInfo userInfo1 = userService.checkAuthInfo(userRoleAuthModel.getUserInfo().getAppId(), userRoleAuthModel.getUserInfo().getAppSecret());
                userInfo.setDataAuth(userInfo1.getDataAuth());
            }
        } else {
            userInfo.setDataAuth(getDefaultDataAuth());
        }
        return userRoleAuthModel;
    }

    @PostMapping("/getUserInfo")
    public UserInfo getUserInfo(@RequestBody ReqParamObject reqParamObject) {
        UserInfo userInfo = null;
        User user = userService.getUserInfo(reqParamObject.getUserId());
        if (null != user) {
            userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUserName(user.getUserName());
            userInfo.setRole(user.getRole());
            userInfo.setPhone(user.getPhone());
            userInfo.setTag(user.getTag());
            userInfo.setAppId(user.getAppId());
            userInfo.setAppSecret(user.getAppSecret());
            userInfo.setAuthGroupId(user.getAuthGroupId());
        } else {
            if (0 == IsPrivatization) {
                userInfo = userService.getUserDevopsByUserId(reqParamObject.getUserId());
                List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                if (null != individuations && individuations.size() > 0) {
                    userInfo.setUserName(individuations.get(0).getCompanyName());
                }
            }
        }
        return userInfo;
    }

    /***
     *
     * 分页获取用户信息
     * ****/
    @PostMapping("/getUserListByRole")
    public ReponseMessage getUserListByRole(String queryName, Integer userId) {
        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "获取用户列表成功");

        if (null == userId) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 1);
            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无访问权限");
            }
            String tag = "";
            if (!(userRoleAuthModel.getUserInfo().getRole() == 2 || userRoleAuthModel.getUserInfo().getRole() == 1)) {
                tag = userRoleAuthModel.getRoleJurisdiction().getTag() + "_" + userRoleAuthModel.getRoleJurisdiction().getId();
            }
            List<User> users = userService.searchUserList(queryName, tag, userRoleAuthModel.getUserInfo().getAppId());

            List<UserModel> userModels = new ArrayList<UserModel>();
            if (users.size() > 0) {
                for (User user : users) {
                    UserModel userModel = new UserModel();
                    BeanUtils.copyProperties(user, userModel);
                    RoleJurisdiction roleJurisdiction = roleJurisdictionService.selectByPrimaryKey(user.getRole());
                    User user1 = userService.selectByPrimaryKey(user.getCreatorId());
                    if (null != user1) {
                        userModel.setCreatorName(user1.getUserName());
                    } else {
                        UserInfo userInfo = userService.getUserDevopsByUserId(user.getCreatorId());
                        if (null != userInfo) {
                            List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                            if (null == individuations || individuations.size() <= 0) {
                                userModel.setCreatorName(userInfo.getCompany());
                            } else {
                                userModel.setCreatorName(individuations.get(0).getCompanyName());
                            }
                        }
                    }
                    if (null != roleJurisdiction && StringUtils.isNotEmpty(roleJurisdiction.getRoleName())) {
                        userModel.setRoleName(roleJurisdiction.getRoleName());
                    }
                    userModels.add(userModel);
                }
            }

            reponseMessage.setAttachObject(userModels);
            return reponseMessage;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /***
     *
     * 分页获取用户信息
     * ****/
    @PostMapping("/getUserByPage")
    public ReponseMessage getUserByPage(String queryName, Integer userId, Integer pageNumber, Integer pageSize) {
        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "获取用户列表成功");

        if (null == userId || null == pageNumber || null == pageSize) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 1);
            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无访问权限");
            }
            String tag = "";
            if (!(userRoleAuthModel.getUserInfo().getRole() == 2 || userRoleAuthModel.getUserInfo().getRole() == 1)) {
                tag = userRoleAuthModel.getRoleJurisdiction().getTag() + "_" + userRoleAuthModel.getRoleJurisdiction().getId();
            }
            PageResult<User> users = userService.searchUserByPage(queryName, tag, userRoleAuthModel.getUserInfo().getAppId(), pageNumber, pageSize);

            List<UserModel> userModels = new ArrayList<UserModel>();
            if (users.getResults().size() > 0) {
                for (User user : users.getResults()) {
                    UserModel userModel = new UserModel();
                    BeanUtils.copyProperties(user, userModel);
                    RoleJurisdiction roleJurisdiction = roleJurisdictionService.selectByPrimaryKey(user.getRole());
                    User user1 = userService.selectByPrimaryKey(user.getCreatorId());
                    if (null != user1) {
                        userModel.setCreatorName(user1.getUserName());
                    } else {
                        UserInfo userInfo = userService.getUserDevopsByUserId(user.getCreatorId());
                        if (null != userInfo) {
                            List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                            if (null == individuations || individuations.size() <= 0) {
                                userModel.setCreatorName(userInfo.getCompany());
                            } else {
                                userModel.setCreatorName(individuations.get(0).getCompanyName());
                            }
                        }
                    }
                    if (null != roleJurisdiction && StringUtils.isNotEmpty(roleJurisdiction.getRoleName())) {
                        userModel.setRoleName(roleJurisdiction.getRoleName());
                    }
                    userModels.add(userModel);
                }
            }

            PageResult<UserModel> userPageResult = new PageResult<UserModel>();
            userPageResult.setResults(userModels);
            userPageResult.setPageNumber(users.getPageNumber());
            userPageResult.setPageSize(users.getPageSize());
            userPageResult.setTotal(users.getTotal());
            reponseMessage.setAttachObject(userPageResult);
            return reponseMessage;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PostMapping("/getUserList")
    public ReponseMessage getUserList(String queryName, Integer userId, Integer projectId) {
        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "获取用户列表成功");

        if (null == userId) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }
        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 1);
            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无访问权限");
            }
            String tag = "";
            if (!(userRoleAuthModel.getUserInfo().getRole() == 2 || userRoleAuthModel.getUserInfo().getRole() == 1)) {
                tag = userRoleAuthModel.getRoleJurisdiction().getTag() + "_" + userRoleAuthModel.getRoleJurisdiction().getId();
            }
            List<User> users = userService.searchUserList(queryName, tag, userRoleAuthModel.getUserInfo().getAppId());
            List<User> userList = new ArrayList<User>();
            if (null != projectId && null != users && users.size() > 0) {
                for (User user : users) {
                    if (userRoleAuthModel.getUserInfo().getRole() == 2 || userRoleAuthModel.getUserInfo().getRole() == 1) {
                        userList.add(user);
                    } else {
                        RoleJurisdiction roleJurisdiction = roleJurisdictionService.selectByPrimaryKey(user.getRole());
                        if (StringUtils.isNotEmpty(roleJurisdiction.getConfigJson())) {
                            boolean flag = false;
                            List<ProjectRole> projectRoles = JSON.parseArray(roleJurisdiction.getConfigJson(), ProjectRole.class);
                            for (ProjectRole projectRole : projectRoles) {
                                if (projectRole.getProjectId().intValue() == projectId.intValue()) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                userList.add(user);
                            }
                        }
                    }
                }
            }
            reponseMessage.setAttachObject(userList);
            return reponseMessage;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 检测账号是否存在
     *
     * @return
     */
    @PostMapping("/isAccount")
    public boolean isAccount(String account) {
        try {
            boolean flag = false;

            User Info = userService.getByAccount(account);
            if (IsPrivatization == 0) {
                if (null == Info) {
                    flag = userService.getUserByAccount(account);
                } else {
                    flag = true;
                }

            } else if (1 == IsPrivatization) {
                if (null != Info) {
                    flag = true;
                }
            }
            return flag;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PostMapping("/loginCommoner")
    public ReponseMessage loginCommoner(@Valid User user, BindingResult result, String ip, String address) {

        if (result.hasErrors()) {
            String message = CollectErrorUtil.CollectError(result);
            return ReponseMessage.Create(ReponseMessage.S_Fail, message);
        }
        try {
            UserInfo userInfo = null;

            User Info = userService.getByAccount(user.getAccount());
            if (IsPrivatization == 0) {
                if (null == Info) {
                    userInfo = userService.getUserByDevops(user.getAccount(), user.getPassword());
                    if (null == userInfo) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "账号或密码错误");
                    }
                    if (0 == userInfo.getStatus()) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                    }
                } else {
                    if (Info.getDeleteTag() == 1) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "无此用户");
                    }
                    if (1 == Info.getLocked()) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                    }

                    UserInfo userBusiness = null;
                    JSONObject paramObj = new JSONObject();
                    paramObj.put("appId", Info.getAppId());
                    paramObj.put("appSecret", Info.getAppSecret());
                    String body = JSON.toJSONString(paramObj);
                    String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/getInfoByAppId", body);
                    if (null != responseBody) {
                        JSONObject result1 = JSONObject.parseObject(responseBody);
                        if (null != result1 && "0000".equals(result1.getString("code"))) {
                            userBusiness = JSON.parseObject(JSON.toJSONString(result1.getJSONObject("attachObject")), UserInfo.class);
                        }
                    }

                    if (null == userBusiness) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "暂无当前企业账号信息");
                    }
                    if (0 == userBusiness.getStatus()) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "当前企业处于禁用状态");
                    }
                    userInfo = GetUserInfoFromUser(Info);
                    userInfo.setAppId(Info.getAppId());
                    userInfo.setAppSecret(Info.getAppSecret());
                }

            } else if (1 == IsPrivatization) {
                if (null == Info || Info.getDeleteTag() == 1) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "无此用户");
                }

                if (1 == Info.getLocked()) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                }

                userInfo = GetUserInfoFromUser(Info);
                userInfo.setAppId(Info.getAppId());
                userInfo.setAppSecret(Info.getAppSecret());
                userInfo.setDataAuth(getDefaultDataAuth());
            }

            if (StringUtils.isEmpty(userInfo.getCompany())) {
                List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                if (null != individuations && individuations.size() >= 0)
                    userInfo.setCompany(individuations.get(0).getCompanyName());
            }

            if (StringUtils.isEmpty(userInfo.getUserName())) {
                userInfo.setUserName(userInfo.getCompany());
            }

            if (StringUtils.isEmpty(userInfo.getHeadPortrait())) {
                userInfo.setHeadPortrait(baseUrl + headPortrait + "/user/defaultLogo.png");
            }

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
            token.setRememberMe(true);
            subject.login(token);

            String sessionId = subject.getSession().getId().toString();
            String accountLower = user.getAccount().toLowerCase();
            jedisManager.setSessionValuesByKey("pc_token_" + accountLower, sessionId);
            userInfo.setTokenId(sessionId);

            ReponseMessage message = ReponseMessage.Create(ReponseMessage.S_Success, "登录成功");
            UserRoleAuthModel userRoleAuthModel = roleJurisdictionService.getUserAuthInfo(userInfo);


            UserLoginInfoModel userLoginInfoModel = new UserLoginInfoModel(userInfo, getAuthInfo(userRoleAuthModel));
            message.setAttachObject(userLoginInfoModel);
            this.insertLog(1, userInfo.getAccount() + "(" + (userInfo.getRole() == 2 ? userInfo.getCompany() : userInfo.getUserName()) + ")", 1, StringUtils.isNotEmpty(ip) ? ip : "", StringUtils.isNotEmpty(address) ? address : "", userInfo.getAppId());
            return message;

        } catch (UnknownAccountException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "该用户不存在");
        } catch (LockedAccountException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "该用户被禁用");
        } catch (AuthenticationException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "用户名或密码错误");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ReponseMessage.Create(ReponseMessage.S_Fail, "登录失败");
        }
    }

    @PostMapping("/LoginWeChat")
    public ReponseMessage LoginWeChat(@Valid User user, BindingResult result, String ip, String address) {

        if (result.hasErrors()) {
            String message = CollectErrorUtil.CollectError(result);
            return ReponseMessage.Create(ReponseMessage.S_Fail, message);
        }
        try {
            UserInfo userInfo = null;

            User Info = userService.getByAccount(user.getAccount());
            if (IsPrivatization == 0) {
                if (null == Info) {
                    userInfo = userService.getUserByDevops(user.getAccount(), user.getPassword());
                    if (null == userInfo) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "账号或密码错误");
                    }

                    if (0 == userInfo.getStatus()) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                    }
                } else {
                    if (Info.getDeleteTag() == 1) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "无此用户");
                    }
                    if (1 == Info.getLocked()) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                    }

                    UserInfo userBusiness = null;
                    JSONObject paramObj = new JSONObject();
                    paramObj.put("appId", user.getAppId());
                    paramObj.put("appSecret", user.getAppSecret());
                    String body = JSON.toJSONString(paramObj);
                    String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/getInfoByAppId", body);
                    if (null != responseBody) {
                        JSONObject result1 = JSONObject.parseObject(responseBody);
                        if (null != result1 && "0000".equals(result1.getString("code"))) {
                            userBusiness = JSON.parseObject(JSON.toJSONString(result1.getJSONObject("attachObject")), UserInfo.class);
                        }
                    }
                    if (null == userBusiness || userBusiness.getStatus() == 0) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "暂无当前企业账号信息");
                    }
                    userInfo = GetUserInfoFromUser(Info);
                    userInfo.setAppId(Info.getAppId());
                    userInfo.setAppSecret(Info.getAppSecret());
                }

            } else if (1 == IsPrivatization) {
                if (null == Info || Info.getDeleteTag() == 1) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "无此用户");
                }

                if (1 == Info.getLocked()) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                }
                userInfo = GetUserInfoFromUser(Info);
                userInfo.setDataAuth(getDefaultDataAuth());
                userInfo.setAppId(Info.getAppId());
                userInfo.setAppSecret(Info.getAppSecret());
            }

            if (StringUtils.isEmpty(userInfo.getCompany())) {
                List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                if (null != individuations && individuations.size() >= 0)
                    userInfo.setCompany(individuations.get(0).getCompanyName());
            }

            if (StringUtils.isEmpty(userInfo.getUserName())) {
                userInfo.setUserName(userInfo.getCompany());
            }

            if (StringUtils.isEmpty(userInfo.getHeadPortrait())) {
                userInfo.setHeadPortrait(baseUrl + headPortrait + "/user/defaultLogo.png");
            }

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
            token.setRememberMe(true);
            subject.login(token);

            String sessionId = subject.getSession().getId().toString();
            String accountLower = user.getAccount().toLowerCase();
            jedisManager.setSessionValuesByKey("wx_token_" + accountLower, sessionId);
            userInfo.setTokenId(sessionId);

            ReponseMessage message = ReponseMessage.Create(ReponseMessage.S_Success, "登录成功");
            UserRoleAuthModel userRoleAuthModel = roleJurisdictionService.getUserAuthInfo(userInfo);

            UserLoginInfoModel userLoginInfoModel = new UserLoginInfoModel(userInfo, getAuthInfo(userRoleAuthModel));
            message.setAttachObject(userLoginInfoModel);

            this.insertLog(1, userInfo.getAccount() + "(" + (userInfo.getRole() == 2 ? userInfo.getCompany() : userInfo.getUserName()) + ")", 2, StringUtils.isNotEmpty(ip) ? ip : "", StringUtils.isNotEmpty(address) ? address : "", userInfo.getAppId());

            return message;

        } catch (UnknownAccountException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "该用户不存在");
        } catch (LockedAccountException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "该用户被禁用");
        } catch (AuthenticationException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "用户名或密码错误");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ReponseMessage.Create(ReponseMessage.S_Fail, "登录失败");
        }
    }

    @PostMapping("/LoginApp")
    public ReponseMessage LoginApp(@Valid User user, BindingResult result, String ip, String address) {

        if (result.hasErrors()) {
            String message = CollectErrorUtil.CollectError(result);
            return ReponseMessage.Create(ReponseMessage.S_Fail, message);
        }
        try {
            UserInfo userInfo = null;

            User Info = userService.getByAccount(user.getAccount());
            if (IsPrivatization == 0) {
                if (null == Info) {
                    userInfo = userService.getUserByDevops(user.getAccount(), user.getPassword());
                    if (null == userInfo) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "账号或密码错误");
                    }
                    if (0 == userInfo.getStatus()) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                    }
                } else {
                    if (Info.getDeleteTag() == 1) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "无此用户");
                    }
                    if (1 == Info.getLocked()) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                    }

                    UserInfo userBusiness = null;
                    JSONObject paramObj = new JSONObject();
                    paramObj.put("appId", user.getAppId());
                    paramObj.put("appSecret", user.getAppSecret());
                    String body = JSON.toJSONString(paramObj);
                    String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/getInfoByAppId", body);
                    if (null != responseBody) {
                        JSONObject result1 = JSONObject.parseObject(responseBody);
                        if (null != result1 && "0000".equals(result1.getString("code"))) {
                            userBusiness = JSON.parseObject(JSON.toJSONString(result1.getJSONObject("attachObject")), UserInfo.class);
                        }
                    }
                    if (null == userBusiness || userBusiness.getStatus() == 0) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "暂无当前企业账号信息");
                    }

                    if (0 == userBusiness.getStatus()) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                    }

                    userInfo = GetUserInfoFromUser(Info);
                    userInfo.setAppId(Info.getAppId());
                    userInfo.setAppSecret(Info.getAppSecret());
                }

            } else if (1 == IsPrivatization) {
                if (null == Info || Info.getDeleteTag() == 1) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "无此用户");
                }
                if (1 == Info.getLocked()) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "当前用户处于禁用状态");
                }
                userInfo = GetUserInfoFromUser(Info);
                userInfo.setDataAuth(getDefaultDataAuth());
                userInfo.setAppId(Info.getAppId());
                userInfo.setAppSecret(Info.getAppSecret());
            }

            if (StringUtils.isEmpty(userInfo.getCompany())) {
                List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                if (null != individuations && individuations.size() >= 0)
                    userInfo.setCompany(individuations.get(0).getCompanyName());
            }

            if (StringUtils.isEmpty(userInfo.getUserName())) {
                userInfo.setUserName(userInfo.getCompany());
            }

            if (StringUtils.isEmpty(userInfo.getHeadPortrait())) {
                userInfo.setHeadPortrait(baseUrl + headPortrait + "/user/defaultLogo.png");
            }

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
            token.setRememberMe(true);
            subject.login(token);

            String sessionId = subject.getSession().getId().toString();
            String accountLower = user.getAccount().toLowerCase();
            jedisManager.setSessionValuesByKey("app_token_" + accountLower, sessionId);
            userInfo.setTokenId(sessionId);

            ReponseMessage message = ReponseMessage.Create(ReponseMessage.S_Success, "登录成功");
            UserRoleAuthModel userRoleAuthModel = roleJurisdictionService.getUserAuthInfo(userInfo);

            UserLoginInfoModel userLoginInfoModel = new UserLoginInfoModel(userInfo, getAuthInfo(userRoleAuthModel));
            message.setAttachObject(userLoginInfoModel);

            this.insertLog(1, userInfo.getAccount() + "(" + (userInfo.getRole() == 2 ? userInfo.getCompany() : userInfo.getUserName()) + ")", 3, StringUtils.isNotEmpty(ip) ? ip : "", StringUtils.isNotEmpty(address) ? address : "", userInfo.getAppId());

            return message;

        } catch (UnknownAccountException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "该用户不存在");
        } catch (LockedAccountException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "该用户被禁用");
        } catch (AuthenticationException ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "用户名或密码错误");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ReponseMessage.Create(ReponseMessage.S_Fail, "登录失败");
        }
    }

    @PostMapping("/loginout")
    public ReponseMessage Loginout(HttpServletRequest httpServletRequest) {
        try {
            HttpServletRequest httpServlet = httpServletRequest;
            String authorizationStr = httpServlet.getHeader("Authorization");
            String[] strArr = authorizationStr.split(" ");
            Base64.Decoder decoder = Base64.getDecoder();
            String userAndPwdStr = new String(decoder.decode(strArr[1]), "UTF-8");
            String[] userInfoArr = userAndPwdStr.split(":");

            User userInfo = userService.getByAccount(StringUtils.trim(userInfoArr[0]));
            if (null == userInfo && IsPrivatization == 0) {
                userInfo = new User();
                UserInfo user = userService.getUserByDevops(StringUtils.trim(userInfoArr[0]), StringUtils.trim(userInfoArr[1]));
                userInfo.setAccount(user.getAccount());
                userInfo.setAppId(user.getAppId());
                if (StringUtils.isEmpty(userInfo.getUserName())) {
                    List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                    if (null != individuations && individuations.size() >= 0)
                        userInfo.setUserName(individuations.get(0).getCompanyName());
                }
            }
            this.insertLog(2, userInfo.getAccount() + "(" + userInfo.getUserName() + ")", 0, "", "", userInfo.getAppId());
            SecurityUtils.getSubject().logout();
            return ReponseMessage.Create(ReponseMessage.S_Success, "登出成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ReponseMessage.Create(ReponseMessage.S_Success, "登出异常");
        }
    }

    @PostMapping("/getUserInfoById")
    public UserInfo getUserInfoById(Integer userId) {
        UserInfo userInfo = null;
        if (null != userId) {
            User user = userService.selectByPrimaryKey(userId);
            if (IsPrivatization == 0) {
                if (null != user) {
                    userInfo = GetUserInfoFromUser(user);
                } else {
                    userInfo = userService.getUserDevopsByUserId(userId);
                }
            } else {
                if (null != user) {
                    userInfo = GetUserInfoFromUser(user);
                }
            }
        }
        return userInfo;
    }

    /**
     * 修改用户信息
     *
     * @param userId
     * @param user
     * @param result
     * @param headPortraitFile
     * @return
     */
    @PostMapping("/updateUser")
    public ReponseMessage updateUser(Integer userId, @Valid User user, BindingResult result, @RequestParam(value = "headPortraitFile", required = false) MultipartFile headPortraitFile) {

        if (null == userId || result.hasErrors()) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        User opUser = userService.selectByPrimaryKey(userId);
        if (null != opUser && opUser.getAccount().equals(experience)) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
        }
        try {
            if (0 == IsPrivatization) {
                User user1 = userService.selectByPrimaryKey(user.getId());
                if (null != user1) {

                    if (null != headPortraitFile) {
                        String headPortraitUrl = OSSImageUtils.UploadPicFile(headPortraitFile, "user/" + user1.getAccount());
                        user1.setHeadPortrait(headPortraitUrl);
                    }
                    user1.setUserName(user.getUserName());
                    user1.setPhone(user.getPhone());
                    user1.setRole(user.getRole());
                    user1.setEmail(user.getEmail());
                    userService.updateUserInfo(user1);
                } else {
                    UserInfo userInfo = userService.getUserDevopsByUserId(user.getId());
                    if (null != userInfo) {
                        JSONObject paramObj = new JSONObject();
                        paramObj.put("userId", user.getId());
                        paramObj.put("company", user.getUserName());
                        String body = JSON.toJSONString(paramObj);
                        String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/updateCompanyName", body);
                        return JSONObject.parseObject(responseBody, ReponseMessage.class);
                    } else {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "用户不存在");
                    }
                }
            } else {
                User user1 = userService.getUserInfo(user.getId());
                if (null == user1) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "用户不存在");
                }
                if (null != headPortraitFile) {
                    String headPortraitUrl = OSSImageUtils.UploadPicFile(headPortraitFile, "user/" + user1.getAccount());
                    user1.setHeadPortrait(headPortraitUrl);
                }
                user1.setUserName(user.getUserName());
                user1.setEmail(user.getEmail());
                user1.setPhone(user.getPhone());
                user1.setRole(user.getRole());
                userService.updateUserInfo(user1);
            }
            return ReponseMessage.Create(ReponseMessage.S_Success, "用户信息更新成功");
        } catch (Exception ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "用户信息更新异常");
        }
    }

    /**
     * 修改用户密码
     *
     * @param user
     * @param result
     * @return
     */
    @PostMapping("/setUserPassword")
    public ReponseMessage setUserPassword(Integer userId, @Valid UserLoginCheck user, BindingResult result) {
        if (null == userId || result.hasErrors()) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        User opUser = userService.selectByPrimaryKey(userId);
        if (null != opUser && opUser.getAccount().equals(experience)) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
        }
        try {
            if (null != opUser) {
                String pw = opUser.getPassword();
                opUser.setPassword(user.getOrgPassword());
                String targetPassword = userService.getNewPassword(opUser);
                if (!targetPassword.equals(pw)) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "原密码不正确");
                }

                if (!user.getNewPassword().equals(user.getConfirmPassword())) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "新密码与确认密码不一致");
                }

                if (user.getOrgPassword().equals(user.getNewPassword())) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "新密码与原密码相同，请更换");
                }
                userService.changePassword(opUser.getAccount(), user.getNewPassword());
                userService.clearAuthenticationCache();
            } else {
                if (1 == IsPrivatization) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "用户不存在");
                }
                UserInfo userInfo = userService.getUserDevopsByUserId(userId);
                if (null != userInfo) {
                    JSONObject paramObj = new JSONObject();
                    paramObj.put("userId", user.getUserid());
                    paramObj.put("orgPassword", user.getOrgPassword());
                    paramObj.put("newPassword", user.getNewPassword());
                    paramObj.put("confirmPassword", user.getConfirmPassword());
                    String body = JSON.toJSONString(paramObj);
                    String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/setUserPwd", body);
                    return JSONObject.parseObject(responseBody, ReponseMessage.class);
                } else {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "用户不存在");
                }
            }
            return ReponseMessage.Create(ReponseMessage.S_Success, "修改密码成功");
        } catch (Exception ex) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, "修改密码异常");
        }
    }

    /***
     * 重置密码
     *
     * ****/
    @PostMapping("/resetPassword")
    public ReponseMessage resetPassword(Integer userId, @Valid User user, BindingResult result) {
        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "密码重置成功，重置后密码是：" + user.getPassword());
        if (result.hasErrors()) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, CollectErrorUtil.CollectError(result));
        }
        try {
            if (null == userId) {
                return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
            }

            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 2);

            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无编辑权限");
            }
            if (user.getAccount().equals(experience)) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
            }
            userService.changePassword(user.getAccount(), user.getPassword());
            userService.clearAuthenticationCache();

            return reponseMessage;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 注册账户
     *
     * @param user
     * @param result
     * @return
     */
    @PostMapping("/register")
    public ReponseMessage register(Integer userId, @RequestParam(value = "headPortraitFile", required = false) MultipartFile headPortraitFile, @Valid User user, BindingResult result) {
        if (null == userId || result.hasErrors()) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 2);

            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无编辑权限");
            }

            if (userRoleAuthModel.getUserInfo().getAccount().equals(experience)) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
            }

            if (userRoleAuthModel.getRoleJurisdiction().getIsAdmin() == 3) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "普通用户无创建权限");
            }

            if (isAccount(user.getAccount())) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "账号已存在");
            }

            Integer currentCount = 0;
            Integer userCount = 0;
            UserInfo userBusiness = null;
            User user1 = userService.getUserInfo(userId);
            if (IsPrivatization == 0) {
                if (null != user1) {

                    JSONObject paramObj = new JSONObject();
                    paramObj.put("appId", user.getAppId());
                    paramObj.put("appSecret", user.getAppSecret());
                    String body = JSON.toJSONString(paramObj);
                    String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/getInfoByAppId", body);
                    if (null != responseBody) {
                        JSONObject result1 = JSONObject.parseObject(responseBody);
                        if (null != result1) {
                            String code = result1.getString("code");
                            if ("0000".equals(code)) {
                                JSONObject attach = result1.getJSONObject("attachObject");
                                if (null != attach) {
                                    userBusiness = JSON.parseObject(JSON.toJSONString(attach), UserInfo.class);
                                }
                            }
                        }
                    }
                    if (null == userBusiness) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "查无此账号相关信息");
                    }
                    currentCount = userService.getUserCountByAppId(user1.getAppId(), 0);
                } else {
                    userBusiness = userService.getUserDevopsByUserId(userId);
                    if (null == userBusiness) {
                        return ReponseMessage.Create(ReponseMessage.S_Fail, "查无此账号相关信息");
                    }
                    currentCount = userService.getUserCountByAppId(userBusiness.getAppId(), 0);
                }
                userCount = userBusiness.getDataAuth().getUserCount();

                if (userCount <= currentCount) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "账号创建已上限");
                }
            }

            if (null != headPortraitFile) {
                String headPortraitUrl = OSSImageUtils.UploadPicFile(headPortraitFile, "user/" + user.getAccount());
                user.setHeadPortrait(headPortraitUrl);
            } else {
                user.setHeadPortrait(OSSImageUtils.getDefaultheadPortraitFile());
            }
            user.setLocked(0);
            if (StringUtils.isNotEmpty(userRoleAuthModel.getUserInfo().getAuthGroupId()))
                user.setAuthGroupId(userRoleAuthModel.getUserInfo().getAuthGroupId());
            user.setAppId(userRoleAuthModel.getUserInfo().getAppId());
            user.setAppSecret(userRoleAuthModel.getUserInfo().getAppSecret());

            if (userRoleAuthModel.getRoleJurisdiction().getId() == 1 || userRoleAuthModel.getRoleJurisdiction().getId() == 2) {
                user.setTag("2");
            } else {
                user.setTag(userRoleAuthModel.getRoleJurisdiction().getTag() + "_" + userRoleAuthModel.getRoleJurisdiction().getId());
            }
            user.setDeleteTag(0);
            user.setCreateTime(new Date());
            user.setCreatorId(userId);
            userService.insert(user);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ReponseMessage.Create(ReponseMessage.S_Fail, "注册用户失敗");
        }
        return ReponseMessage.Create(ReponseMessage.S_Success, "注册用户成功");
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @PostMapping("/deleteUser")
    public ReponseMessage deleteUser(Integer userId, @RequestParam(value = "ids[]") Integer[] ids) {
        if (null == userId || null == ids || ids.length <= 0) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }
        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 3);

            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无刪除权限");
            }
            if (userRoleAuthModel.getUserInfo().getAccount().equals(experience)) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
            }
            for (Integer id : ids) {
                User user = userService.selectByPrimaryKey(id);
                user.setDeleteTag(1);
                user.setId(id);
                userService.updateByPrimaryKey(user);
            }
            return ReponseMessage.Create(ReponseMessage.S_Success, "删除用户成功");
        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * 是否禁用用户
     *
     * @param userId
     * @param id
     * @param locked
     * @return
     */
    @PostMapping("/setLocked")
    public ReponseMessage setLocked(Integer userId, Integer id, Integer locked) {

        if (null == id || null == locked) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 2);

            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无编辑权限");
            }
            if (userRoleAuthModel.getUserInfo().getAccount().equals(experience)) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
            }
            userService.setLocked(id, locked);
            return ReponseMessage.Create(ReponseMessage.S_Success, "用户禁用切换成功");
        } catch (Exception ex) {
            throw ex;
        }

    }

    private DataAuth getDefaultDataAuth() {
        DataAuth dataAuth = new DataAuth();
        dataAuth.setUserCount(userCount);
        dataAuth.setDataCount(dataCount);
        dataAuth.setMsgCount(msgCount);
        dataAuth.setWebcamCount(webcamCount);
        dataAuth.setIsLogoAndName(1);
        dataAuth.setDataTotalCount(dataTotalCount);
        return dataAuth;
    }

    public static UserInfo GetUserInfoFromUser(User info) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(info.getId());
        userInfo.setAccount(info.getAccount());
        if (StringUtils.isNotEmpty(info.getUserName())) userInfo.setUserName(info.getUserName());
        userInfo.setPassword(info.getPassword());
        userInfo.setPhone(info.getPhone());
        userInfo.setEmail(info.getEmail());
        userInfo.setHeadPortrait(info.getHeadPortrait());
        userInfo.setRole(info.getRole());
        userInfo.setLocked(info.getLocked());
        userInfo.setCreatorId(info.getCreatorId());
        userInfo.setTag(info.getTag());
        userInfo.setCreateTime(info.getCreateTime());
        userInfo.setAuthGroupId(info.getAuthGroupId());
        userInfo.setAppId(info.getAppId());
        userInfo.setAppSecret(info.getAppSecret());
        return userInfo;
    }

    private UserRoleModel getAuthInfo(UserRoleAuthModel userRoleAuthModel) {

        List<MenuInfo> menuInfos = new ArrayList<MenuInfo>();
        for (MenuInfo menuInfo : userRoleAuthModel.getMenuChineses()) {
            if (null != menuInfo.getReadStatus() && menuInfo.getReadStatus() == 1) {
                menuInfos.add(menuInfo);
            }
        }
        List<MenuInfo> menuInfoEs = new ArrayList<MenuInfo>();
        for (MenuInfo menuInfo : userRoleAuthModel.getMenuEnglishes()) {
            if (null != menuInfo.getReadStatus() && menuInfo.getReadStatus() == 1) {
                menuInfoEs.add(menuInfo);
            }
        }

        List<MenuInfo> menuInfoTree = new ArrayList<MenuInfo>();
        for (MenuInfo menuInfo : menuInfos) {
            if (null == menuInfo.getPid() || 0 == menuInfo.getPid()) {
                menuInfoTree.add(findChildren2(menuInfo, menuInfos));
            }
        }

        List<MenuInfo> menuInfoETree = new ArrayList<MenuInfo>();
        for (MenuInfo menuInfo : menuInfoEs) {
            if (null == menuInfo.getPid() || 0 == menuInfo.getPid()) {
                menuInfoETree.add(findChildren2(menuInfo, menuInfoEs));
            }
        }

        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setMenuChineseModels(menuInfoTree);
        userRoleModel.setMenuEnglishModels(menuInfoETree);

        if (null != userRoleAuthModel.getRoleJurisdiction())
            userRoleModel.setRoleJurisdiction(userRoleAuthModel.getRoleJurisdiction());

        return userRoleModel;
    }

    private MenuInfo findChildren2(MenuInfo menuInfo, List<MenuInfo> menuInfos) {
        for (MenuInfo item : menuInfos) {
            if (null != item.getPid() && menuInfo.getMid().intValue() == item.getPid().intValue()) {
                if (menuInfo.getChildren() == null) {
                    menuInfo.setChildren(new ArrayList<MenuInfo>());
                }
                menuInfo.getChildren().add(findChildren2(item, menuInfos));
            }
        }
        return menuInfo;
    }

    private UserRoleAuthModel getAuthResult(int userId, int type) { //type来源请求类型 1:查看 2：编辑 3：删除
        UserRoleAuthModel userRoleAuthModel = new UserRoleAuthModel();
        UserInfo userInfo;
        User user = userService.getUserInfo(userId);
        if (null != user) {
            userInfo = new UserInfo();
            BeanUtils.copyProperties(user, userInfo);
        } else {
            userInfo = userService.getUserDevopsByUserId(userId);
        }
        if (null == userInfo || (1 != type && userInfo.getAccount().equals(experience))) {
            return null;
        } else {
            userRoleAuthModel.setUserInfo(userInfo);
        }
        RoleJurisdiction roleJurisdiction = roleJurisdictionService.selectByPrimaryKey(userInfo.getRole());
        if (null == roleJurisdiction) {
            return null;
        } else {
            userRoleAuthModel.setRoleJurisdiction(roleJurisdiction);
        }
        List<MenuRole> menuRoles = menuRoleService.getMenuRoleByRole(userInfo.getRole());
        List<MenuModel> menuModels = null;
        if (null != menuRoles && menuRoles.size() > 0) {
            menuModels = menuService.getMenuList(menuRoles);
        }

        if (!(2 == userInfo.getRole() || 1 == userInfo.getRole() || roleJurisdiction.getIsAdmin() == 2)) {
            boolean flag = true;
            for (MenuModel menuModel : menuModels) {
                if (null != menuModel && StringUtils.isNotEmpty(menuModel.getPath())) {
                    switch (type) {
                        case 1:
                            if (menuModel.getPath().indexOf("/user/userManager") >= 0 && (menuModel.getReadStatus() == 1 || menuModel.getEditStatus() == 1 || menuModel.getDeleteStatus() == 1)) {
                                flag = false;
                            }
                            break;
                        case 2:
                            if (menuModel.getPath().indexOf("/user/userManager") >= 0 && (menuModel.getEditStatus() == 1 || menuModel.getDeleteStatus() == 1)) {
                                flag = false;
                            }
                            break;
                        case 3:
                            if (menuModel.getPath().indexOf("/user/userManager") >= 0 && (menuModel.getDeleteStatus() == 1)) {
                                flag = false;
                            }
                            break;
                    }
                }
                if (flag) {
                    return null;
                }
            }
        }
        return userRoleAuthModel;
    }

    private ReponseMessage insertLog(Integer type, String operationer, Integer operation_mode, String visit_address, String address, String appId) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        JSONObject jsonDevice = new JSONObject();
        jsonDevice.put("type", type);
        jsonDevice.put("operationer", operationer);
        jsonDevice.put("operation_mode", operation_mode);
        jsonDevice.put("visit_address", visit_address);
        jsonDevice.put("address", address);
        jsonDevice.put("appId", appId);
        jsonDevice.put("createTime", new Date());

        HttpEntity<String> requestDevice = new HttpEntity<>(JSON.toJSONString(jsonDevice), requestHeaders);
        ReponseMessage reponseMessage = restTemplate.postForObject(system_url + "/log/insertLog", requestDevice, ReponseMessage.class);
        return reponseMessage;
    }
}
