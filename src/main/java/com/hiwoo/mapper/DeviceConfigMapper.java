package com.hiwoo.mapper;

import com.hiwoo.entity.DeviceConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface DeviceConfigMapper extends Mapper<DeviceConfig> {

    List<DeviceConfig> getDataByDeviceId(List<String> ids);
}
