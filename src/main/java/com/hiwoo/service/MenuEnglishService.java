package com.hiwoo.service;

import com.hiwoo.entity.MenuEnglish;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface MenuEnglishService extends BaseMapper<MenuEnglish> {

    List<MenuEnglish> getMenuEnglishByRole(Integer role);

    int insertAndGetId(MenuEnglish menuEnglish);

}
