package com.hiwoo.service.impl;

import com.hiwoo.entity.MenuEnglish;
import com.hiwoo.mapper.MenuEnglishMapper;
import com.hiwoo.service.MenuEnglishService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuEnglishServiceImpl implements MenuEnglishService {

    @Resource
    MenuEnglishMapper menuEnglishMapper;

    @Override
    public List<MenuEnglish> getMenuEnglishByRole(Integer role) {
        Example example = new Example(MenuEnglish.class);
        Example.Criteria cri = example.createCriteria();
        if (role != null && role >  0) {
            cri.andEqualTo("role",role);
        }
        return menuEnglishMapper.selectByExample(example);
    }

    @Override
    public int insertAndGetId(MenuEnglish menuEnglish) {
        return menuEnglishMapper.insertAndGetId(menuEnglish);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return menuEnglishMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(MenuEnglish menuEnglish) {
        return menuEnglishMapper.delete(menuEnglish);
    }

    @Override
    public int insert(MenuEnglish menuEnglish) {
        return menuEnglishMapper.insert(menuEnglish);
    }

    @Override
    public int insertSelective(MenuEnglish menuEnglish) {
        return menuEnglishMapper.insertSelective(menuEnglish);
    }

    @Override
    public List<MenuEnglish> selectAll() {
        return menuEnglishMapper.selectAll();
    }

    @Override
    public MenuEnglish selectByPrimaryKey(Object o) {
        return menuEnglishMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(MenuEnglish menuEnglish) {
        return menuEnglishMapper.selectCount(menuEnglish);
    }

    @Override
    public List<MenuEnglish> select(MenuEnglish menuEnglish) {
        return menuEnglishMapper.select(menuEnglish);
    }

    @Override
    public MenuEnglish selectOne(MenuEnglish menuEnglish) {
        return menuEnglishMapper.selectOne(menuEnglish);
    }

    @Override
    public int updateByPrimaryKey(MenuEnglish menuEnglish) {
        return menuEnglishMapper.updateByPrimaryKey(menuEnglish);
    }

    @Override
    public int updateByPrimaryKeySelective(MenuEnglish menuEnglish) {
        return menuEnglishMapper.updateByPrimaryKey(menuEnglish);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return false;
    }
}
