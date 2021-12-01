package com.hiwoo.service.impl;

import com.hiwoo.entity.DeviceConfig;
import com.hiwoo.mapper.DeviceConfigMapper;
import com.hiwoo.service.DeviceConfigService;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceConfigServiceImpl implements DeviceConfigService {

    @Resource
    private DeviceConfigMapper deviceConfigMapper;

    @Override
    public List<DeviceConfig> getDataByDeviceId(List<String> ids) {
        return deviceConfigMapper.getDataByDeviceId(ids);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return deviceConfigMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(DeviceConfig deviceConfig) {
        Example example = new Example(DeviceConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (StringUtils.isNotEmpty(deviceConfig.getBoxId())) {
            cri.andEqualTo("boxId", deviceConfig.getBoxId());
        }
        if (null != deviceConfig.getDeviceId() && deviceConfig.getDeviceId() > 0) {
            cri.andEqualTo("deviceId", deviceConfig.getDeviceId());
        }
        return deviceConfigMapper.deleteByExample(example);
    }

    @Override
    public int insert(DeviceConfig deviceConfig) {
        return deviceConfigMapper.insert(deviceConfig);
    }

    @Override
    public int insertSelective(DeviceConfig deviceConfig) {
        return deviceConfigMapper.insertSelective(deviceConfig);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return deviceConfigMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<DeviceConfig> selectAll() {
        return deviceConfigMapper.selectAll();
    }

    @Override
    public DeviceConfig selectByPrimaryKey(Object o) {
        return deviceConfigMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(DeviceConfig deviceConfig) {
        return deviceConfigMapper.selectCount(deviceConfig);
    }

    @Override
    public List<DeviceConfig> select(DeviceConfig deviceConfig) {
        Example example = new Example(DeviceConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (StringUtils.isNotEmpty(deviceConfig.getBoxId())) {
            cri.andEqualTo("boxId", deviceConfig.getBoxId());
        }
        if (null != deviceConfig.getDeviceId() && deviceConfig.getDeviceId() > 0) {
            cri.andEqualTo("deviceId", deviceConfig.getDeviceId());
        }
        return deviceConfigMapper.selectByExample(example);
    }

    @Override
    public DeviceConfig selectOne(DeviceConfig deviceConfig) {
        return deviceConfigMapper.selectOne(deviceConfig);
    }

    @Override
    public int updateByPrimaryKey(DeviceConfig deviceConfig) {
        return deviceConfigMapper.updateByPrimaryKey(deviceConfig);
    }

    @Override
    public int updateByPrimaryKeySelective(DeviceConfig deviceConfig) {
        return deviceConfigMapper.updateByPrimaryKeySelective(deviceConfig);
    }
}
