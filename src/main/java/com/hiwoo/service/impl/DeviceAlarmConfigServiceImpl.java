package com.hiwoo.service.impl;

import com.hiwoo.entity.DeviceAlarmConfig;
import com.hiwoo.mapper.DeviceAlarmConfigMapper;
import com.hiwoo.service.DeviceAlarmConfigService;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceAlarmConfigServiceImpl implements DeviceAlarmConfigService {

    @Resource
    private DeviceAlarmConfigMapper deviceAlarmConfigMapper;

    @Override
    public List<DeviceAlarmConfig> getAlramByDeviceId(List<String> ids) {
        return deviceAlarmConfigMapper.getAlramByDeviceId(ids);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return deviceAlarmConfigMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(DeviceAlarmConfig deviceAlarmConfig) {
        Example example = new Example(DeviceAlarmConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (null != deviceAlarmConfig.getDeviceId() && deviceAlarmConfig.getDeviceId() > 0) {
            cri.andEqualTo("deviceId", deviceAlarmConfig.getDeviceId());
        }
        if (StringUtils.isNotEmpty(deviceAlarmConfig.getRuleCode())) {
            cri.andEqualTo("ruleCode", deviceAlarmConfig.getRuleCode());
        }
        return deviceAlarmConfigMapper.deleteByExample(example);
    }

    @Override
    public int insert(DeviceAlarmConfig deviceAlarmConfig) {
        return deviceAlarmConfigMapper.insert(deviceAlarmConfig);
    }

    @Override
    public int insertSelective(DeviceAlarmConfig deviceAlarmConfig) {
        return deviceAlarmConfigMapper.insertSelective(deviceAlarmConfig);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return deviceAlarmConfigMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<DeviceAlarmConfig> selectAll() {
        return deviceAlarmConfigMapper.selectAll();
    }

    @Override
    public DeviceAlarmConfig selectByPrimaryKey(Object o) {
        return deviceAlarmConfigMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(DeviceAlarmConfig deviceAlarmConfig) {
        return deviceAlarmConfigMapper.selectCount(deviceAlarmConfig);
    }

    @Override
    public List<DeviceAlarmConfig> select(DeviceAlarmConfig deviceAlarmConfig) {
        Example example = new Example(DeviceAlarmConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (null != deviceAlarmConfig.getDeviceId() && deviceAlarmConfig.getDeviceId() > 0) {
            cri.andEqualTo("deviceId", deviceAlarmConfig.getDeviceId());
        }
        if (StringUtils.isNotEmpty(deviceAlarmConfig.getRuleCode())) {
            cri.andEqualTo("ruleCode", deviceAlarmConfig.getRuleCode());
        }
        return deviceAlarmConfigMapper.selectByExample(example);
    }

    @Override
    public DeviceAlarmConfig selectOne(DeviceAlarmConfig deviceAlarmConfig) {
        return deviceAlarmConfigMapper.selectOne(deviceAlarmConfig);
    }

    @Override
    public int updateByPrimaryKey(DeviceAlarmConfig deviceAlarmConfig) {
        return deviceAlarmConfigMapper.updateByPrimaryKey(deviceAlarmConfig);
    }

    @Override
    public int updateByPrimaryKeySelective(DeviceAlarmConfig deviceAlarmConfig) {
        return deviceAlarmConfigMapper.updateByPrimaryKeySelective(deviceAlarmConfig);
    }
}
