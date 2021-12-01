package com.hiwoo.service;

import com.hiwoo.entity.BoxAlarmConfig;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Set;

public interface BoxAlarmConfigService extends BaseMapper<BoxAlarmConfig> {

    List<BoxAlarmConfig> getAlarmByCodes(Set<String> codes);
}
