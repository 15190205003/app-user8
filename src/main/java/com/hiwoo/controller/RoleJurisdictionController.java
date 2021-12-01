package com.hiwoo.controller;

import com.alibaba.fastjson.JSON;
import com.hiwoo.entity.*;
import com.hiwoo.model.*;
import com.hiwoo.service.*;
import com.hiwoo.utils.CollectErrorUtil;
import com.hiwoo.utils.PageResult;
import com.hiwoo.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/role")
public class RoleJurisdictionController {

    @Value("${experience.user}")
    private String experience;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleJurisdictionService roleJurisdictionService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRoleService menuRoleService;

    @Autowired
    private DeviceGroupService deviceGroupService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceConfigService deviceConfigService;

    @Autowired
    private DeviceAlarmConfigService deviceAlarmConfigService;

    @Autowired
    private DeviceEventConfigService deviceEventConfigService;

    @Autowired
    private BoxAlarmConfigService boxAlarmConfigService;

    @Autowired
    private BoxEventConfigService boxEventConfigService;

    @Autowired
    private IndividuationService individuationService;

    @Value("${IsPrivatization}")
    private Integer IsPrivatization;

    /**
     * 分页获取角色列表
     *
     * @param roleName
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping("/getRoleByPage")
    public ReponseMessage getRoleByPage(String roleName, Integer userId, Integer pageNumber, Integer pageSize) {
        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "获取角色列表成功");
        if (null == userId || null == pageNumber || null == pageSize) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }
        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 1);
            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无查看权限");
            }

            String tag = "";
            if (!(userRoleAuthModel.getUserInfo().getRole() == 2 || userRoleAuthModel.getUserInfo().getRole() == 1)) {
                if (StringUtils.isNotEmpty(userRoleAuthModel.getRoleJurisdiction().getTag())) {
                    tag = userRoleAuthModel.getRoleJurisdiction().getTag() + "_" + userRoleAuthModel.getRoleJurisdiction().getId();
                }
            }

            PageResult<RoleJurisdiction> pageResultList = roleJurisdictionService.searchRoleByPage(roleName, tag, userRoleAuthModel.getUserInfo().getAppId(), pageNumber, pageSize);
            PageResult<RoleJurisdictionModel> pageResult = new PageResult<RoleJurisdictionModel>();

            if (null != pageResultList.getResults() && pageResultList.getResults().size() > 0) {
                List<RoleJurisdictionModel> roleJurisdictions = new ArrayList<RoleJurisdictionModel>();
                if (null != pageResultList.getResults() && pageResultList.getResults().size() > 0) {
                    for (RoleJurisdiction roleJurisdiction : pageResultList.getResults()) {
                        RoleJurisdictionModel roleJurisdictionModel = new RoleJurisdictionModel();
                        BeanUtils.copyProperties(roleJurisdiction, roleJurisdictionModel);
                        if (roleJurisdiction.getUserId() == 0) {
                            roleJurisdictionModel.setCreatorName("系统管理者");
                        } else {
                            User user1 = userService.selectByPrimaryKey(roleJurisdiction.getUserId());
                            if (null != user1) {
                                roleJurisdictionModel.setCreatorName(user1.getUserName());
                            } else {
                                UserInfo userInfo = userService.getUserDevopsByUserId(roleJurisdiction.getUserId());
                                if (null != userInfo) {
                                    List<Individuation> individuations = individuationService.select(new Individuation(userInfo.getAppId()));
                                    if (null == individuations || individuations.size() <= 0) {
                                        roleJurisdictionModel.setCreatorName(userInfo.getCompany());
                                    } else {
                                        roleJurisdictionModel.setCreatorName(individuations.get(0).getCompanyName());
                                    }
                                }
                            }
                        }
                        roleJurisdictionModel.setAppId(userRoleAuthModel.getUserInfo().getAppId());
                        roleJurisdictions.add(roleJurisdictionModel);
                    }
                    pageResult.setResults(roleJurisdictions);
                    pageResult.setTotal(pageResultList.getTotal());
                    pageResult.setPageSize(pageResultList.getPageSize());
                    pageResult.setPageNumber(pageResultList.getPageNumber());
                }
            }
            reponseMessage.setAttachObject(pageResult);
            return reponseMessage;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 下拉框获取角色列表
     *
     * @param userId
     * @return
     */
    @PostMapping("/getRoleList")
    public ReponseMessage getRoleList(Integer userId) {
        if (null == userId) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }
        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "获取角色列表成功");

        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 1);
            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无查看权限");
            }

            String tag = "";
            if (!(userRoleAuthModel.getUserInfo().getRole() == 2 || userRoleAuthModel.getUserInfo().getRole() == 1)) {
                if (StringUtils.isNotEmpty(userRoleAuthModel.getRoleJurisdiction().getTag())) {
                    tag = userRoleAuthModel.getRoleJurisdiction().getTag() + "_" + userRoleAuthModel.getRoleJurisdiction().getId();
                }
            }
            List<RoleJurisdiction> roleJurisdictions = roleJurisdictionService.getJurisdictionList(tag, userRoleAuthModel.getUserInfo().getAppId());

            reponseMessage.setAttachObject(roleJurisdictions);
            return reponseMessage;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 获取角色信息
     *
     * @param id
     * @return
     */
    @PostMapping("/getRoleInfoById")
    public ReponseMessage getRoleInfoById(Integer userId, Integer id) {
        if (null == id || null == userId) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "获取角色信息成功");
        UserInfo userInfo = null;

        if (0 == IsPrivatization) {
            User user = userService.selectByPrimaryKey(userId);
            if (null != user) {
                userInfo = new UserInfo();
                BeanUtils.copyProperties(user, userInfo);
            } else {
                userInfo = userService.getUserDevopsByUserId(userId);
                if (null == userInfo) {
                    return ReponseMessage.Create(ReponseMessage.S_DataException, "查无此用户");
                }
            }
        } else {
            User user = userService.selectByPrimaryKey(userId);
            if (null == user) {
                return ReponseMessage.Create(ReponseMessage.S_DataException, "查无此用户");
            }
            userInfo = new UserInfo();
            BeanUtils.copyProperties(user, userInfo);
        }
        try {
            RoleJurisdictionModel roleJurisdictionModel = new RoleJurisdictionModel();
            RoleJurisdiction roleJurisdiction = roleJurisdictionService.selectByPrimaryKey(id);
            if (null != roleJurisdiction) {
                BeanUtils.copyProperties(roleJurisdiction, roleJurisdictionModel);
                roleJurisdictionModel.setAppId(userInfo.getAppId());
                List<MenuRole> menuRoles = menuRoleService.select(new MenuRole(roleJurisdiction.getId()));
                if (null != menuRoles && menuRoles.size() > 0) {
                    List<MenuModel> menuModels = menuService.getMenuList(menuRoles);

                    List<MenuModel> menuModelTree = new ArrayList<MenuModel>();
                    for (MenuModel menuModel : menuModels) {
                        if (null == menuModel.getPid() || 0 == menuModel.getPid()) {
                            menuModelTree.add(findChildren2(menuModel, menuModels));
                        }
                    }
                    roleJurisdictionModel.setMenuModelList(menuModelTree);
                }

                if (roleJurisdiction.getIsAdmin() == 3) {
                    if (StringUtils.isNotEmpty(roleJurisdiction.getConfigJson())) {
                        List<ProjectRole> projectRoles = JSON.parseArray(roleJurisdiction.getConfigJson(), ProjectRole.class);
                        if (null != projectRoles && projectRoles.size() > 0) {
                            roleJurisdictionModel.setProjectList(projectRoles);
                        }
                    }
                } else {
                    String appId = "";
                    if (StringUtils.isNotEmpty(roleJurisdictionModel.getAppId())) {
                        appId = roleJurisdictionModel.getAppId();
                    } else {
                        appId = userInfo.getAppId();
                    }
                    List<DeviceGroup> deviceProjects = deviceGroupService.select(new DeviceGroup(null, 0, appId));
                    if (null != deviceProjects && deviceProjects.size() > 0) {
                        List<ProjectRole> projectRoles = new ArrayList<ProjectRole>();
                        for (DeviceGroup deviceGroup : deviceProjects) {
                            ProjectRole projectRole = new ProjectRole();
                            projectRole.setProjectId(deviceGroup.getId());
                            projectRole.setProjectName(deviceGroup.getName());
                            projectRole.setDashboard(1);
                            projectRole.setAnalysis(1);
                            projectRole.setScadaManager(1);
                            projectRole.setScadaTemplate(1);
                            projectRole.setIsAuthor(0);
                            List<DeviceRole> deviceRoles = new ArrayList<DeviceRole>();
                            List<Device> devices = new ArrayList<Device>();
                            List<Device> deviceList = deviceService.select(new Device(deviceGroup.getId()));
                            if (null != deviceList && deviceList.size() > 0) {
                                devices.addAll(deviceList);
                            } else {
                                List<DeviceGroup> deviceGroups = deviceGroupService.select(new DeviceGroup(deviceGroup.getId().toString()));
                                if (null != deviceGroups && deviceGroups.size() > 0) {
                                    for (DeviceGroup deviceGroup1 : deviceGroups) {
                                        List<Device> deviceArr = deviceService.select(new Device(deviceGroup1.getId()));
                                        if (null != deviceArr && deviceArr.size() > 0) devices.addAll(deviceArr);
                                    }
                                }
                            }
                            if (null != devices && devices.size() > 0) {
                                for (Device device : devices) {
                                    DeviceRole deviceRole = this.deviceFormatDeviceRole(device);
                                    deviceRole.setDeviceName(device.getDeviceName());
                                    deviceRole.setDeviceId(device.getId());
                                    deviceRole.setAlarmStatus(1);
                                    deviceRole.setEventStatus(1);
                                    deviceRole.setDataStatus(1);
                                    deviceRole.setWebcamStatus(1);
                                    deviceRole.setDeleteStatus(1);
                                    deviceRole.setReadStatus(1);
                                    deviceRole.setEditStatus(1);
                                    deviceRole.setMapStatus(1);
                                    deviceRole.setScadaStatus(1);
                                    deviceRoles.add(deviceRole);
                                }
                                projectRole.setDeviceList(deviceRoles);
                            }
                            projectRoles.add(projectRole);
                        }

                        if (roleJurisdiction.getIsAdmin() == 2) {
                            if (StringUtils.isNotEmpty(roleJurisdiction.getConfigJson())) {
                                List<ProjectRole> projectRoleList = JSON.parseArray(roleJurisdiction.getConfigJson(), ProjectRole.class);
                                if (null != projectRoleList && projectRoleList.size() > 0) {
                                    for (int i = 0; i < projectRoleList.size(); i++) {
                                        for (ProjectRole projectRole1 : projectRoles) {
                                            if (projectRoleList.get(i).getProjectId().intValue() == projectRole1.getProjectId().intValue()) {
                                                projectRoleList.get(i).setProjectName(projectRole1.getProjectName());
                                                projectRoleList.get(i).setDeviceList(projectRole1.getDeviceList());
                                            }
                                        }
                                    }
                                    roleJurisdictionModel.setProjectList(projectRoleList);
                                    roleJurisdiction.setConfigJson(JSON.toJSONString(projectRoleList));
                                    roleJurisdictionService.updateByPrimaryKey(roleJurisdiction);
                                }
                            }
                        } else {
                            roleJurisdictionModel.setProjectList(projectRoles);
                        }
                    }
                }
            }
            reponseMessage.setAttachObject(roleJurisdictionModel);
            return reponseMessage;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 创建角色
     */
    @PostMapping("/createRole")
    public ReponseMessage createRole(Integer userId, @Valid RoleJurisdiction roleJurisdiction, BindingResult result, String menu, String deviceConfigJson) {
        if (null == userId) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }
        if (StringUtils.isEmpty(menu)) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "至少分配一个访问权限");
        } else {
            List<MenuInfo> menuInfos = JSON.parseArray(menu, MenuInfo.class);
            if (menuInfos.size() <= 0) {
                return ReponseMessage.Create(ReponseMessage.S_DataException, "至少分配一个访问权限");
            }
        }

        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "创建角色成功");
        if (result.hasErrors()) {
            return ReponseMessage.Create(ReponseMessage.S_Fail, CollectErrorUtil.CollectError(result));
        }

        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 2);

            if (null == userRoleAuthModel || userRoleAuthModel.getRoleJurisdiction().getIsAdmin() == 3) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无权限编辑");
            }
            if (userRoleAuthModel.getUserInfo().getAccount().equals(experience)) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
            }

            boolean flag = roleJurisdictionService.isRoleName(roleJurisdiction.getRoleName(), userRoleAuthModel.getUserInfo().getAppId());

            if (flag) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "角色名称重复");
            }

            if (userRoleAuthModel.getUserInfo().getRole() == 2 || userRoleAuthModel.getUserInfo().getRole() == 1) {
                roleJurisdiction.setTag("2");
            } else {
                roleJurisdiction.setTag(userRoleAuthModel.getRoleJurisdiction().getTag() + "_" + userRoleAuthModel.getRoleJurisdiction().getId());
            }
            roleJurisdiction.setAppId(userRoleAuthModel.getUserInfo().getAppId());
            roleJurisdiction.setCreateTime(new Date());
            roleJurisdiction.setConfigJson(this.formatJurisdiction(roleJurisdiction, deviceConfigJson));

            if (roleJurisdiction.getIsAdmin() == 2) {
                roleJurisdiction.setAlertMsg(1);
            }
            roleJurisdictionService.insertAndGetId(roleJurisdiction);
            Integer id = roleJurisdiction.getId();
            List<MenuRole> menuRoles = JSON.parseArray(menu, MenuRole.class);

            for (MenuRole menuRole : menuRoles) {
                menuRole.setRoleId(id);
                menuRoleService.insert(menuRole);
            }

            return reponseMessage;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 修改角色
     */
    @PostMapping("/updateRole")
    public ReponseMessage updateRole(Integer userId, @Valid RoleJurisdiction roleJurisdiction, BindingResult result, String menu, String deviceConfigJson) {
        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "修改角色成功");

        if (null == userId || result.hasErrors()) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        try {
            if (roleJurisdiction.getId() == 2 || roleJurisdiction.getId() == 1) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "当前角色不支持编辑");
            }

            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 2);
            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无权限编辑");
            }

            if (userRoleAuthModel.getUserInfo().getAccount().equals(experience)) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
            }

            List<MenuRoleModel> menuRoleModels = JSON.parseArray(menu, MenuRoleModel.class);
            if (roleJurisdiction.getIsAdmin() == 3) {
                if (menuRoleModels.size() <= 0) {
                    return ReponseMessage.Create(ReponseMessage.S_DataException, "至少分配一个访问权限");
                }
                List<MenuRole> menuRoles = menuRoleService.select(new MenuRole(roleJurisdiction.getId()));
                for (MenuRole menuRole : menuRoles) {
                    boolean bool = true;
                    for (MenuRoleModel menuRoleModel : menuRoleModels) {
                        if (menuRole.getMid().intValue() == menuRoleModel.getMid().intValue()) {
                            menuRole.setReadStatus(menuRoleModel.getReadStatus());
                            menuRole.setEditStatus(menuRoleModel.getEditStatus());
                            menuRole.setDeleteStatus(menuRoleModel.getDeleteStatus());
                            menuRoleService.updateByPrimaryKey(menuRole);
                            bool = false;
                        }
                    }
                    if (bool) {
                        menuRoleService.deleteByPrimaryKey(menuRole);
                    }
                }
            }
            for (MenuRoleModel menuRoleModel : menuRoleModels) {
                List<MenuRole> menuRoles = menuRoleService.select(new MenuRole(roleJurisdiction.getId(), menuRoleModel.getMid()));
                if (null == menuRoles || menuRoles.size() <= 0) {
                    MenuRole menuRole = new MenuRole();
                    menuRole.setRoleId(roleJurisdiction.getId());
                    menuRole.setMid(menuRoleModel.getMid());
                    menuRole.setReadStatus(menuRoleModel.getReadStatus());
                    menuRole.setEditStatus(menuRoleModel.getEditStatus());
                    menuRole.setDeleteStatus(menuRoleModel.getDeleteStatus());
                    menuRoleService.insert(menuRole);
                } else {
                    MenuRole menuRole = menuRoles.get(0);
                    menuRole.setReadStatus(menuRoleModel.getReadStatus());
                    menuRole.setEditStatus(menuRoleModel.getEditStatus());
                    menuRole.setDeleteStatus(menuRoleModel.getDeleteStatus());
                    menuRoleService.updateByPrimaryKey(menuRole);
                }
            }

            RoleJurisdiction roleJurisdictionUpd = roleJurisdictionService.selectByPrimaryKey(roleJurisdiction.getId());
            roleJurisdictionUpd.setRoleName(roleJurisdiction.getRoleName());
            roleJurisdictionUpd.setIsAdmin(roleJurisdiction.getIsAdmin());
            roleJurisdictionUpd.setRoleDescribe(roleJurisdiction.getRoleDescribe());
            roleJurisdictionUpd.setAlertMsg(roleJurisdiction.getAlertMsg());
            roleJurisdictionUpd.setConfigJson(this.formatJurisdiction(roleJurisdiction, deviceConfigJson));
            roleJurisdictionService.updateByPrimaryKey(roleJurisdictionUpd);
            return reponseMessage;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 删除角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping("/deleteRole")
    public ReponseMessage deleteRole(Integer userId, @RequestParam(value = "roleIds[]") Integer[] roleIds) {

        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, "删除角色成功");

        if (null == userId || null == roleIds || roleIds.length <= 0) {
            return ReponseMessage.Create(ReponseMessage.S_DataException, "参数异常");
        }

        try {
            UserRoleAuthModel userRoleAuthModel = this.getAuthResult(userId, 3);
            if (null == userRoleAuthModel) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "无删除权限");
            }
            if (userRoleAuthModel.getUserInfo().getAccount().equals(experience)) {
                return ReponseMessage.Create(ReponseMessage.S_Fail, "当前为体验账号！");
            }

            for (Integer id : roleIds) {
                List<User> users = userService.select(new User(id, 0));
                if (null != users && users.size() > 0) {
                    return ReponseMessage.Create(ReponseMessage.S_Fail, "当前角色已绑定用户，用户名为：" + users.get(0).getUserName());
                }
            }

            for (Integer id : roleIds) {
                roleJurisdictionService.deleteByPrimaryKey(id);
                menuRoleService.delete(new MenuRole(id));
            }
            return reponseMessage;
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/getRoleJurisdiction")
    public RoleJurisdiction getRoleJurisdiction(@RequestBody ReqParamObject reqParamObject) {
        if (null == reqParamObject.getRoleId() || reqParamObject.getRoleId() <= 0) {
            return null;
        }
        RoleJurisdiction roleJurisdiction = roleJurisdictionService.selectByPrimaryKey(reqParamObject.getRoleId());
        return roleJurisdiction;
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
        } else {
            return null;
        }

        if (!(2 == userInfo.getRole() || 1 == userInfo.getRole() || roleJurisdiction.getIsAdmin() == 2)) {
            boolean flag = true;

            for (MenuModel menuModel : menuModels) {
                if (null != menuModel && StringUtils.isNotEmpty(menuModel.getPath())) {
                    switch (type) {
                        case 1:
                            if (menuModel.getPath().indexOf("/user/roleManager") >= 0 && (menuModel.getReadStatus() == 1 || menuModel.getEditStatus() == 1 || menuModel.getDeleteStatus() == 1)) {
                                flag = false;
                            }
                            break;
                        case 2:
                            if (menuModel.getPath().indexOf("/user/roleManager") >= 0 && (menuModel.getEditStatus() == 1 || menuModel.getDeleteStatus() == 1)) {
                                flag = false;
                            }
                            break;
                        case 3:
                            if (menuModel.getPath().indexOf("/user/roleManager") >= 0 && (menuModel.getDeleteStatus() == 1)) {
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

    private DeviceRole deviceFormatDeviceRole(Device device) {
        DeviceRole deviceRole = new DeviceRole();

        List<String> ids = new ArrayList<>();
        ids.add(device.getId().toString());

        List<DataRule> dataRules = new ArrayList<DataRule>();
        List<AlarmEventRule> alarmRules = new ArrayList<AlarmEventRule>();
        List<AlarmEventRule> eventRules = new ArrayList<AlarmEventRule>();
        List<WebcamRule> webcamRules = new ArrayList<WebcamRule>();

        List<DeviceConfig> deviceConfigs = deviceConfigService.getDataByDeviceId(ids);
        if (null != deviceConfigs && deviceConfigs.size() > 0) {
            for (DeviceConfig deviceConfig : deviceConfigs) {
                if (StringUtils.isNotEmpty(deviceConfig.getDataConfig())) {
                    List<DataInfo> dataInfoList = JSON.parseArray(deviceConfig.getDataConfig(), DataInfo.class);
                    if (null != dataInfoList && dataInfoList.size() > 0) {
                        for (DataInfo dataInfo : dataInfoList) {
                            DataRule dataRule = new DataRule();
                            dataRule.setBoxId(deviceConfig.getBoxId());
                            dataRule.setDataIndex(dataInfo.getDataIndex());
                            dataRule.setDataName(dataInfo.getDataName());
                            dataRule.setReadStatus(1);
                            dataRule.setWriteStatus(1);
                            dataRules.add(dataRule);
                        }
                    }
                }
            }
            deviceRole.setDataList(dataRules);
        }
        List<DeviceAlarmConfig> deviceAlarmConfigs = deviceAlarmConfigService.getAlramByDeviceId(ids);

        if (null != deviceAlarmConfigs && deviceAlarmConfigs.size() > 0) {
            Set<String> codes = new HashSet<String>();
            for (DeviceAlarmConfig deviceAlarmConfig : deviceAlarmConfigs) {
                codes.add(deviceAlarmConfig.getRuleCode());
            }
            if (null != codes && codes.size() > 0) {
                List<BoxAlarmConfig> boxAlarmConfigs = boxAlarmConfigService.getAlarmByCodes(codes);
                for (BoxAlarmConfig boxAlarmConfig : boxAlarmConfigs) {
                    AlarmEventRule alarmEventRule = new AlarmEventRule();
                    alarmEventRule.setName(boxAlarmConfig.getRuleName());
                    alarmEventRule.setCode(boxAlarmConfig.getRuleCode());
                    alarmEventRule.setCallStatus(1);
                    alarmRules.add(alarmEventRule);
                }
                deviceRole.setAlarmList(alarmRules);
            }
        }

        List<DeviceEventConfig> deviceEventConfigs = deviceEventConfigService.getEventByDeviceId(ids);

        if (null != deviceEventConfigs && deviceEventConfigs.size() > 0) {
            Set<String> codes = new HashSet<String>();
            for (DeviceEventConfig deviceEventConfig : deviceEventConfigs) {
                codes.add(deviceEventConfig.getRuleCode());
            }

            if (null != codes && codes.size() > 0) {
                List<BoxEventConfig> boxEventConfigs = boxEventConfigService.getEventByCodes(codes);
                for (BoxEventConfig boxEventConfig : boxEventConfigs) {
                    AlarmEventRule alarmEventRule = new AlarmEventRule();
                    alarmEventRule.setName(boxEventConfig.getRuleName());
                    alarmEventRule.setCode(boxEventConfig.getRuleCode());
                    alarmEventRule.setCallStatus(1);
                    eventRules.add(alarmEventRule);
                }
                deviceRole.setEvnetList(eventRules);
            }
        }


        List<Webcam> webcams = JSON.parseArray(device.getWebcamConfig(), Webcam.class);
        if (null != webcams && webcams.size() > 0) {
            for (Webcam webcam : webcams) {
                WebcamRule webcamRule = new WebcamRule();
                webcamRule.setCallStatus(1);
                webcamRule.setCode(webcam.getCode());
                webcamRule.setName(webcam.getName());
                webcamRules.add(webcamRule);
            }
            deviceRole.setWebcamList(webcamRules);
        }
        return deviceRole;
    }

    private MenuModel findChildren2(MenuModel menuModel, List<MenuModel> menuModels) {
        for (MenuModel item : menuModels) {
            if (null != item.getPid() && menuModel.getMid().intValue() == item.getPid().intValue()) {
                if (menuModel.getChildren() == null) {
                    menuModel.setChildren(new ArrayList<MenuModel>());
                }
                menuModel.getChildren().add(findChildren2(item, menuModels));
            }
        }
        return menuModel;
    }

    private String formatJurisdiction(RoleJurisdiction roleJurisdiction, String deviceConfigJson) {
        String configJson = "";
        List<ProjectRole> projectRoles = null;
        if (StringUtils.isNotEmpty(roleJurisdiction.getConfigJson())) {
            projectRoles = JSON.parseArray(roleJurisdiction.getConfigJson(), ProjectRole.class);
        }
        List<DeviceRole> deviceRoles = null;
        if (StringUtils.isNotEmpty(deviceConfigJson)) {
            deviceRoles = JSON.parseArray(deviceConfigJson, DeviceRole.class);
        }

        if (null != deviceRoles && deviceRoles.size() > 0) {
            for (DeviceRole deviceRole : deviceRoles) {
                boolean flag = true;
                if (null != projectRoles && projectRoles.size() > 0) {
                    for (ProjectRole projectRole : projectRoles) {
                        if (deviceRole.getProjectId().intValue() == projectRole.getProjectId().intValue()) {
                            if (null != projectRole.getDeviceList() && projectRole.getDeviceList().size() > 0) {
                                projectRole.getDeviceList().add(deviceRole);
                            } else {
                                List<DeviceRole> deviceRoleList = new ArrayList<DeviceRole>();
                                deviceRoleList.add(deviceRole);
                                projectRole.setDeviceList(deviceRoleList);
                            }
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    ProjectRole projectRole = new ProjectRole();
                    projectRole.setProjectName(deviceRole.getProjectName());
                    projectRole.setProjectId(deviceRole.getProjectId());
                    projectRole.setScadaTemplate(0);
                    projectRole.setScadaManager(0);
                    projectRole.setAnalysis(0);
                    projectRole.setDashboard(0);
                    List<DeviceRole> deviceRoleList = new ArrayList<DeviceRole>();
                    deviceRoleList.add(deviceRole);
                    projectRole.setDeviceList(deviceRoleList);
                    projectRoles.add(projectRole);
                }
            }
        }
        configJson = JSON.toJSONString(projectRoles);
        return configJson;
    }
}
