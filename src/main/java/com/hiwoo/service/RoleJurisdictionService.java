package com.hiwoo.service;

import com.hiwoo.entity.RoleJurisdiction;
import com.hiwoo.model.UserInfo;
import com.hiwoo.model.UserRoleAuthModel;
import com.hiwoo.utils.PageResult;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface RoleJurisdictionService extends BaseMapper<RoleJurisdiction> {


    List<RoleJurisdiction> getJurisdictionList(String tag, String appId);

    PageResult<RoleJurisdiction> searchRoleByPage(String queryName, String tag, String appId, int pageNumber, int pageSize);

    int insertAndGetId(RoleJurisdiction roleJurisdiction);

    UserRoleAuthModel getUserAuthInfo(UserInfo userInfo);

    boolean isRoleName(String roleName, String appId);
}
