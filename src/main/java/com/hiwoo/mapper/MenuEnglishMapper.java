package com.hiwoo.mapper;

import com.hiwoo.entity.MenuEnglish;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface MenuEnglishMapper  extends Mapper<MenuEnglish>{

    int insertAndGetId(MenuEnglish menuEnglish);

}
