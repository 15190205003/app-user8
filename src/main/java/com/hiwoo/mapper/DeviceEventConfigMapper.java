package com.hiwoo.mapper;

import com.hiwoo.entity.DeviceEventConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface DeviceEventConfigMapper extends Mapper<DeviceEventConfig> {

    List<DeviceEventConfig> getEventByDeviceId(List<String> ids);

}
