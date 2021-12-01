package com.hiwoo.service;

import com.hiwoo.entity.DeviceAlarmConfig;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;


public interface DeviceAlarmConfigService extends BaseMapper<DeviceAlarmConfig> {

    List<DeviceAlarmConfig> getAlramByDeviceId(List<String> ids);

}
