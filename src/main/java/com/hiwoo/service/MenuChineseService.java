package com.hiwoo.service;

import com.hiwoo.entity.MenuChinese;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface MenuChineseService extends BaseMapper<MenuChinese> {

    List<MenuChinese> getMenuChineseByRole(Integer role);

    int insertAndGetId(MenuChinese menuChinese);
}
