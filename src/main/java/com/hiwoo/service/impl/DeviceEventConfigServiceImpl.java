package com.hiwoo.service.impl;

import com.hiwoo.entity.DeviceEventConfig;
import com.hiwoo.mapper.DeviceEventConfigMapper;
import com.hiwoo.service.DeviceEventConfigService;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceEventConfigServiceImpl implements DeviceEventConfigService {

    @Resource
    private DeviceEventConfigMapper deviceEventConfigMapper;

    @Override
    public List<DeviceEventConfig> getEventByDeviceId(List<String> ids) {
        return deviceEventConfigMapper.getEventByDeviceId(ids);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return deviceEventConfigMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(DeviceEventConfig deviceEventConfig) {

        Example example = new Example(DeviceEventConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (null != deviceEventConfig.getDeviceId() && deviceEventConfig.getDeviceId() > 0) {
            cri.andEqualTo("deviceId", deviceEventConfig.getDeviceId());
        }
        if (StringUtils.isNotEmpty(deviceEventConfig.getRuleCode())) {
            cri.andEqualTo("ruleCode", deviceEventConfig.getRuleCode());
        }
        return deviceEventConfigMapper.deleteByExample(example);

    }

    @Override
    public int insert(DeviceEventConfig deviceEventConfig) {
        return deviceEventConfigMapper.insert(deviceEventConfig);
    }

    @Override
    public int insertSelective(DeviceEventConfig deviceEventConfig) {
        return deviceEventConfigMapper.insertSelective(deviceEventConfig);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return deviceEventConfigMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<DeviceEventConfig> selectAll() {
        return deviceEventConfigMapper.selectAll();
    }

    @Override
    public DeviceEventConfig selectByPrimaryKey(Object o) {
        return deviceEventConfigMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(DeviceEventConfig deviceEventConfig) {
        return deviceEventConfigMapper.selectCount(deviceEventConfig);
    }

    @Override
    public List<DeviceEventConfig> select(DeviceEventConfig deviceEventConfig) {
        Example example = new Example(DeviceEventConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (null != deviceEventConfig.getDeviceId() && deviceEventConfig.getDeviceId() > 0) {
            cri.andEqualTo("deviceId", deviceEventConfig.getDeviceId());
        }
        if (StringUtils.isNotEmpty(deviceEventConfig.getRuleCode())) {
            cri.andEqualTo("ruleCode", deviceEventConfig.getRuleCode());
        }
        return deviceEventConfigMapper.selectByExample(example);
    }

    @Override
    public DeviceEventConfig selectOne(DeviceEventConfig deviceEventConfig) {
        return deviceEventConfigMapper.selectOne(deviceEventConfig);
    }

    @Override
    public int updateByPrimaryKey(DeviceEventConfig deviceEventConfig) {
        return deviceEventConfigMapper.updateByPrimaryKey(deviceEventConfig);
    }

    @Override
    public int updateByPrimaryKeySelective(DeviceEventConfig deviceEventConfig) {
        return deviceEventConfigMapper.updateByPrimaryKeySelective(deviceEventConfig);
    }
}
