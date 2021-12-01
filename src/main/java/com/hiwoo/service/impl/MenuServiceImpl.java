package com.hiwoo.service.impl;

import com.hiwoo.entity.Menu;
import com.hiwoo.entity.MenuRole;
import com.hiwoo.mapper.MenuMapper;
import com.hiwoo.model.MenuModel;
import com.hiwoo.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.awt.SystemColor.menu;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuModel> getMenuList(List<MenuRole> menuRoles) {
        List<MenuModel> menuModels = new ArrayList<MenuModel>();
        List<Menu> menus = menuMapper.getMenuList(menuRoles);
        if (null != menus && menus.size() > 0) {
            for (Menu menu : menus) {
                MenuModel menuModel = new MenuModel();
                BeanUtils.copyProperties(menu, menuModel);
                for (MenuRole menuRole : menuRoles) {
                    if (menu.getId().intValue() == menuRole.getMid().intValue()) {
                        menuModel.setId(menuRole.getId());
                        menuModel.setMid(menu.getId());
                        menuModel.setReadStatus(menuRole.getReadStatus());
                        menuModel.setEditStatus(menuRole.getEditStatus());
                        menuModel.setDeleteStatus(menuRole.getDeleteStatus());
                        menuModels.add(menuModel);
                        break;
                    }
                }
            }
        }
        return menuModels;
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return menuMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(Menu menu) {
        return menuMapper.delete(menu);
    }

    @Override
    public int insert(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    public int insertSelective(Menu menu) {
        return menuMapper.insertSelective(menu);
    }

    @Override
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }

    @Override
    public Menu selectByPrimaryKey(Object o) {
        return menuMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(Menu menu) {
        return menuMapper.selectCount(menu);
    }

    @Override
    public List<Menu> select(Menu menu) {
        return menuMapper.select(menu);
    }

    @Override
    public Menu selectOne(Menu menu) {
        return menuMapper.selectOne(menu);
    }

    @Override
    public int updateByPrimaryKey(Menu menu) {
        return menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public int updateByPrimaryKeySelective(Menu menu) {
        return menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return false;
    }
}
