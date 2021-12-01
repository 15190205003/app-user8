package com.hiwoo.mapper;

import com.hiwoo.entity.Menu;
import com.hiwoo.entity.MenuRole;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MenuMapper extends Mapper<Menu> {

    List<Menu> getMenuList(List<MenuRole> menuRoles);
}
