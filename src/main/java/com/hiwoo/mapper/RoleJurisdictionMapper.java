package com.hiwoo.mapper;

import com.hiwoo.entity.RoleJurisdiction;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoleJurisdictionMapper extends Mapper<RoleJurisdiction>{

    int insertAndGetId(RoleJurisdiction roleJurisdiction);
}
