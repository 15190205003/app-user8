package com.hiwoo.service.impl;

import com.hiwoo.entity.DeviceGroup;
import com.hiwoo.mapper.DeviceGroupMapper;
import com.hiwoo.service.DeviceGroupService;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceGroupServiceImpl implements DeviceGroupService {

    @Resource
    private DeviceGroupMapper deviceGroupMapper;

    @Override
    public int deleteByPrimaryKey(Object o) {
        return deviceGroupMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(DeviceGroup deviceGroup) {
        return deviceGroupMapper.delete(deviceGroup);
    }

    @Override
    public int insert(DeviceGroup deviceGroup) {
        return deviceGroupMapper.insert(deviceGroup);
    }

    @Override
    public int insertSelective(DeviceGroup deviceGroup) {
        return deviceGroupMapper.insertSelective(deviceGroup);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return deviceGroupMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<DeviceGroup> selectAll() {
        return deviceGroupMapper.selectAll();
    }

    @Override
    public DeviceGroup selectByPrimaryKey(Object o) {
        return deviceGroupMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(DeviceGroup deviceGroup) {
        return deviceGroupMapper.selectCount(deviceGroup);
    }

    @Override
    public List<DeviceGroup> select(DeviceGroup deviceGroup) {

        Example example = new Example(DeviceGroup.class);
        Example.Criteria cri = example.createCriteria();
        cri.andEqualTo("pid", deviceGroup.getPid());

        if (StringUtils.isNotEmpty(deviceGroup.getGroupTag())) {
            cri.andLike("groupTag", deviceGroup.getGroupTag() + "_%").orEqualTo("groupTag", deviceGroup.getGroupTag());
        }

        if (StringUtils.isNotEmpty(deviceGroup.getAppId())) {
            cri.andEqualTo("appId", deviceGroup.getAppId());
        }
        return deviceGroupMapper.selectByExample(example);
    }

    @Override
    public DeviceGroup selectOne(DeviceGroup deviceGroup) {
        return deviceGroupMapper.selectOne(deviceGroup);
    }

    @Override
    public int updateByPrimaryKey(DeviceGroup deviceGroup) {
        return deviceGroupMapper.updateByPrimaryKey(deviceGroup);
    }

    @Override
    public int updateByPrimaryKeySelective(DeviceGroup deviceGroup) {
        return deviceGroupMapper.updateByPrimaryKeySelective(deviceGroup);
    }
}
