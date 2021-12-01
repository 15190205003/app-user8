package com.hiwoo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiwoo.entity.Device;
import com.hiwoo.mapper.DeviceMapper;
import com.hiwoo.service.DeviceService;
import com.hiwoo.utils.HttpUtil;
import com.hiwoo.utils.PageResult;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    DeviceMapper deviceMapper;

    @Override
    public int deleteByPrimaryKey(Object o) {
        return deviceMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(Device device) {
        return deviceMapper.delete(device);
    }

    @Override
    public int insert(Device device) {
        return deviceMapper.insert(device);
    }

    @Override
    public int insertSelective(Device device) {
        return deviceMapper.insertSelective(device);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return deviceMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<Device> selectAll() {
        return deviceMapper.selectAll();
    }

    @Override
    public Device selectByPrimaryKey(Object o) {
        return deviceMapper.selectByPrimaryKey(o);
    }


    @Override
    public int selectCount(Device device) {
        return deviceMapper.selectCount(device);
    }

    @Override
    public List<Device> select(Device device) {
        Example example = new Example(Device.class);
        Example.Criteria cri = example.createCriteria();
        if (null != device.getGroupId() && -1 < device.getGroupId()) {
            cri.andEqualTo("groupId", device.getGroupId());
        }
        return deviceMapper.selectByExample(example);
    }

    @Override
    public Device selectOne(Device device) {
        return deviceMapper.selectOne(device);
    }

    @Override
    public int updateByPrimaryKey(Device device) {
        return deviceMapper.updateByPrimaryKey(device);
    }

    @Override
    public int updateByPrimaryKeySelective(Device device) {
        return deviceMapper.updateByPrimaryKeySelective(device);
    }
}
