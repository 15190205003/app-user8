package com.hiwoo.mapper;

import com.hiwoo.entity.DeviceAlarmConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface DeviceAlarmConfigMapper extends Mapper<DeviceAlarmConfig> {

    List<DeviceAlarmConfig> getAlramByDeviceId(List<String> ids);

}
