package com.hiwoo.service.impl;

import com.hiwoo.entity.MenuRole;
import com.hiwoo.mapper.MenuRoleMapper;
import com.hiwoo.service.MenuRoleService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuRoleServiceImpl implements MenuRoleService {

    @Resource
    private MenuRoleMapper menuRoleMapper;

    @Override
    public List<MenuRole> getMenuRole(MenuRole menuRole) {
        Example example = new Example(MenuRole.class);
        Example.Criteria cri = example.createCriteria();
        if (menuRole.getRoleId() != null && menuRole.getRoleId() > 0) {
            cri.andEqualTo("roleId", menuRole.getRoleId());
        }
        if (menuRole.getMid() != null && menuRole.getMid() > 0) {
            cri.andEqualTo("mid", menuRole.getMid());
        }
        return menuRoleMapper.selectByExample(example);
    }

    @Override
    public List<MenuRole> getMenuRoleByRole(Integer roleId) {
        Example example = new Example(MenuRole.class);
        Example.Criteria cri = example.createCriteria();
        if (roleId != null && roleId > 0) {
            cri.andEqualTo("roleId", roleId);
        }
        return menuRoleMapper.selectByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return menuRoleMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(MenuRole menuRole) {
        Example example = new Example(MenuRole.class);
        Example.Criteria cri = example.createCriteria();
        if (null != menuRole.getRoleId() && menuRole.getRoleId() > 0) {
            cri.andEqualTo("roleId", menuRole.getRoleId());
        }
        return menuRoleMapper.deleteByExample(example);
    }

    @Override
    public int insert(MenuRole menuRole) {
        return menuRoleMapper.insert(menuRole);
    }

    @Override
    public int insertSelective(MenuRole menuRole) {
        return menuRoleMapper.insertSelective(menuRole);
    }

    @Override
    public List<MenuRole> selectAll() {
        return menuRoleMapper.selectAll();
    }

    @Override
    public MenuRole selectByPrimaryKey(Object o) {
        return menuRoleMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(MenuRole menuRole) {
        return menuRoleMapper.selectCount(menuRole);
    }

    @Override
    public List<MenuRole> select(MenuRole menuRole) {
        Example example = new Example(MenuRole.class);
        Example.Criteria cri = example.createCriteria();
        if (null != menuRole.getRoleId() && menuRole.getRoleId() > 0) {
            cri.andEqualTo("roleId", menuRole.getRoleId());
        }
        if (null != menuRole.getMid() && menuRole.getMid() > 0) {
            cri.andEqualTo("mid", menuRole.getMid());
        }
        return menuRoleMapper.selectByExample(example);
    }

    @Override
    public MenuRole selectOne(MenuRole menuRole) {
        return menuRoleMapper.selectOne(menuRole);
    }

    @Override
    public int updateByPrimaryKey(MenuRole menuRole) {
        return menuRoleMapper.updateByPrimaryKey(menuRole);
    }

    @Override
    public int updateByPrimaryKeySelective(MenuRole menuRole) {
        return menuRoleMapper.updateByPrimaryKey(menuRole);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return false;
    }
}
