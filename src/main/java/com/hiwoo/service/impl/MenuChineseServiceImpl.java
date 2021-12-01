package com.hiwoo.service.impl;

import com.hiwoo.entity.MenuChinese;
import com.hiwoo.mapper.MenuChineseMapper;
import com.hiwoo.service.MenuChineseService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuChineseServiceImpl implements MenuChineseService {

    @Resource
    MenuChineseMapper menuChineseMapper;

    @Override
    public List<MenuChinese> getMenuChineseByRole(Integer role) {
        Example example = new Example(MenuChinese.class);
        Example.Criteria cri = example.createCriteria();
        if (role != null && role >  0) {
            cri.andEqualTo("role",role);
        }
        return menuChineseMapper.selectByExample(example);
    }

    @Override
    public int insertAndGetId(MenuChinese menuChinese) {
        return menuChineseMapper.insertAndGetId(menuChinese);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return menuChineseMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(MenuChinese MenuChinese) {
        return menuChineseMapper.delete(MenuChinese);
    }

    @Override
    public int insert(MenuChinese MenuChinese) {
        return menuChineseMapper.insert(MenuChinese);
    }

    @Override
    public int insertSelective(MenuChinese MenuChinese) {
        return menuChineseMapper.insertSelective(MenuChinese);
    }

    @Override
    public List<MenuChinese> selectAll() {
        return menuChineseMapper.selectAll();
    }

    @Override
    public MenuChinese selectByPrimaryKey(Object o) {
        return menuChineseMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(MenuChinese MenuChinese) {
        return menuChineseMapper.selectCount(MenuChinese);
    }

    @Override
    public List<MenuChinese> select(MenuChinese MenuChinese) {
        return menuChineseMapper.select(MenuChinese);
    }

    @Override
    public MenuChinese selectOne(MenuChinese MenuChinese) {
        return menuChineseMapper.selectOne(MenuChinese);
    }

    @Override
    public int updateByPrimaryKey(MenuChinese MenuChinese) {
        return menuChineseMapper.updateByPrimaryKey(MenuChinese);
    }

    @Override
    public int updateByPrimaryKeySelective(MenuChinese MenuChinese) {
        return menuChineseMapper.updateByPrimaryKey(MenuChinese);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return false;
    }
}
