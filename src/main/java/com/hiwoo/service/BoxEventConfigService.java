package com.hiwoo.service;

import com.hiwoo.entity.BoxEventConfig;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Set;

public interface BoxEventConfigService extends BaseMapper<BoxEventConfig> {

    List<BoxEventConfig> getEventByCodes(Set<String> codes);

}
