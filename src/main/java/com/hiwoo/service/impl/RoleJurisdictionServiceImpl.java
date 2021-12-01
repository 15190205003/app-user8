package com.hiwoo.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiwoo.entity.MenuInfo;
import com.hiwoo.entity.MenuRole;
import com.hiwoo.entity.ProjectRole;
import com.hiwoo.entity.RoleJurisdiction;
import com.hiwoo.mapper.RoleJurisdictionMapper;
import com.hiwoo.model.MenuModel;
import com.hiwoo.model.UserInfo;
import com.hiwoo.model.UserRoleAuthModel;
import com.hiwoo.service.MenuService;
import com.hiwoo.service.MenuRoleService;
import com.hiwoo.service.RoleJurisdictionService;
import com.hiwoo.utils.PageResult;
import com.hiwoo.utils.StringUtils;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleJurisdictionServiceImpl implements RoleJurisdictionService {

    @Resource
    private RoleJurisdictionMapper roleJurisdictionMapper;

    @Resource
    MenuService menuService;

    @Resource
    MenuRoleService menuRoleService;

    @Override
    public boolean isRoleName(String roleName, String appId) {
        boolean flag = false;
        Example example = new Example(RoleJurisdiction.class);
        example.createCriteria().andEqualTo("roleName", roleName).andEqualTo("appId", appId);
        List<RoleJurisdiction> roleJurisdictions = roleJurisdictionMapper.selectByExample(example);
        if (null != roleJurisdictions && roleJurisdictions.size() > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public List<RoleJurisdiction> getJurisdictionList(String tag, String appId) {
//        example2.createCriteria().andEqualTo("id", "1").andEqualTo("isAdmin", "1");

        Example example = new Example(RoleJurisdiction.class);
        example.createCriteria().andEqualTo("appId", appId);
        if (StringUtils.isNotEmpty(tag)) {
            example.and(example.createCriteria().andLike("tag", tag + "_%").orEqualTo("tag", tag));
        } else {
            example.or(example.createCriteria().andEqualTo("id", "1").andEqualTo("isAdmin", "1"));
        }
        example.orderBy("createTime").desc();
        return roleJurisdictionMapper.selectByExample(example);
    }

    @Override
    public PageResult<RoleJurisdiction> searchRoleByPage(String queryName, String tag, String appId, int pageNumber, int pageSize) {
//        Example example = new Example(RoleJurisdiction.class);
//        if (StringUtils.isNotEmpty(queryName)) {
//            example.createCriteria().andLike("roleName", "%" + queryName + "%");
//        }
//
//        Example example2 = new Example(RoleJurisdiction.class);
//        example2.createCriteria().andEqualTo("id", "1").andEqualTo("isAdmin", "1");
//
//        Example example3 = new Example(RoleJurisdiction.class);
//        example3.createCriteria().andEqualTo("appId", appId);
//        if (StringUtils.isNotEmpty(tag)) {
//            example3.and(example3.createCriteria().andLike("tag", tag + "_%").orEqualTo("tag", tag));
//        }
//        example2.or(example3.createCriteria());
//        example.and(example2.createCriteria());
//        example.orderBy("createTime").desc();

        Example example = new Example(RoleJurisdiction.class);

        if (StringUtils.isNotEmpty(tag)) {
            example.createCriteria().andEqualTo("appId", appId).andLike("tag", tag + "_%").orEqualTo("tag", tag);
        } else {
            example.createCriteria().andEqualTo("id", "1").andEqualTo("isAdmin", "1").orEqualTo("appId", appId);
        }
        if (StringUtils.isNotEmpty(queryName)) {
            example.and(example.createCriteria().andLike("roleName", "%" + queryName + "%"));
        }
        example.orderBy("createTime").desc();

        PageHelper.startPage(pageNumber, pageSize);

        List<RoleJurisdiction> roleJurisdictions = roleJurisdictionMapper.selectByExample(example);

        PageResult<RoleJurisdiction> pageCollege = new PageResult<RoleJurisdiction>();
        pageCollege.setResults(roleJurisdictions);
        PageInfo<RoleJurisdiction> pageInfo = new PageInfo<RoleJurisdiction>(roleJurisdictions);
        pageCollege.setTotal(pageInfo.getTotal());
        pageCollege.setPageNumber(pageInfo.getPageNum());
        pageCollege.setPageSize(pageInfo.getPageSize());
        return pageCollege;
    }

    @Override
    public int insertAndGetId(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.insertAndGetId(roleJurisdiction);
    }

    @Override
    public UserRoleAuthModel getUserAuthInfo(UserInfo userInfo) {
        UserRoleAuthModel userRoleAuthModel = new UserRoleAuthModel();
        RoleJurisdiction roleJurisdiction = roleJurisdictionMapper.selectByPrimaryKey(userInfo.getRole());

        if (null != roleJurisdiction) {
            userRoleAuthModel.setRoleJurisdiction(roleJurisdiction);

            List<MenuRole> menuRoles = menuRoleService.select(new MenuRole(roleJurisdiction.getId()));
            List<MenuModel> menuModels = menuService.getMenuList(menuRoles);

            if (null != menuModels && menuModels.size() > 0) {
                List<MenuInfo> menuChineses = new ArrayList<MenuInfo>();
                List<MenuInfo> menuEnglishes = new ArrayList<MenuInfo>();
                for (MenuModel menuModel : menuModels) {
                    MenuInfo menuInfo = new MenuInfo();
                    BeanUtils.copyProperties(menuModel, menuInfo);
                    menuInfo.setGuide(menuModel.getGuideChinese());
                    menuChineses.add(menuInfo);
                    MenuInfo menuEInfo = new MenuInfo();
                    BeanUtils.copyProperties(menuModel, menuEInfo);
                    menuEInfo.setGuide(menuModel.getGuideEnglish());
                    menuEnglishes.add(menuEInfo);
                }
                userRoleAuthModel.setMenuChineses(menuChineses);
                userRoleAuthModel.setMenuEnglishes(menuEnglishes);
            }
        } else {
            return null;
        }
        return userRoleAuthModel;
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return roleJurisdictionMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.delete(roleJurisdiction);
    }

    @Override
    public int insert(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.insert(roleJurisdiction);
    }

    @Override
    public int insertSelective(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.insertSelective(roleJurisdiction);
    }

    @Override
    public List<RoleJurisdiction> selectAll() {
        return roleJurisdictionMapper.selectAll();
    }

    @Override
    public RoleJurisdiction selectByPrimaryKey(Object o) {
        return roleJurisdictionMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.selectCount(roleJurisdiction);
    }

    @Override
    public List<RoleJurisdiction> select(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.select(roleJurisdiction);
    }

    @Override
    public RoleJurisdiction selectOne(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.selectOne(roleJurisdiction);
    }

    @Override
    public int updateByPrimaryKey(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.updateByPrimaryKey(roleJurisdiction);
    }

    @Override
    public int updateByPrimaryKeySelective(RoleJurisdiction roleJurisdiction) {
        return roleJurisdictionMapper.updateByPrimaryKeySelective(roleJurisdiction);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return false;
    }
}
