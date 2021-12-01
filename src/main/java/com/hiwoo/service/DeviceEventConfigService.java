package com.hiwoo.service;

import com.hiwoo.entity.DeviceEventConfig;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;


public interface DeviceEventConfigService extends BaseMapper<DeviceEventConfig> {

    List<DeviceEventConfig> getEventByDeviceId(List<String> ids);

}
