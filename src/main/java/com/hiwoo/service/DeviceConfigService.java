package com.hiwoo.service;

import com.hiwoo.entity.DeviceConfig;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface DeviceConfigService extends BaseMapper<DeviceConfig> {

    List<DeviceConfig> getDataByDeviceId(List<String> ids);

}
