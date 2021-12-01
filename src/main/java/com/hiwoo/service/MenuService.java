package com.hiwoo.service;

import com.hiwoo.entity.Menu;
import com.hiwoo.entity.MenuRole;
import com.hiwoo.model.MenuModel;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface MenuService extends BaseMapper<Menu> {

    List<MenuModel> getMenuList(List<MenuRole> menuRoles);
}
