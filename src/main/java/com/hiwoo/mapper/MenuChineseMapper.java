package com.hiwoo.mapper;

import com.hiwoo.entity.MenuChinese;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface MenuChineseMapper extends Mapper<MenuChinese>{

    int insertAndGetId(MenuChinese menuChinese);

}
