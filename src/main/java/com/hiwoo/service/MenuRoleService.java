package com.hiwoo.service;

import com.hiwoo.entity.MenuRole;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface MenuRoleService extends BaseMapper<MenuRole> {

    List<MenuRole> getMenuRoleByRole(Integer role);

    List<MenuRole> getMenuRole(MenuRole menuRole);
}
