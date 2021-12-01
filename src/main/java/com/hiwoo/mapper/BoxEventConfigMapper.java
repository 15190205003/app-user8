package com.hiwoo.mapper;

import com.hiwoo.entity.BoxEventConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;


@Repository
public interface BoxEventConfigMapper extends Mapper<BoxEventConfig> {

    List<BoxEventConfig> getEventByCodes(Set<String> codes);

}
