package com.hiwoo.service.impl;

import com.hiwoo.entity.BoxAlarmConfig;
import com.hiwoo.mapper.BoxAlarmConfigMapper;
import com.hiwoo.service.BoxAlarmConfigService;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BoxAlarmConfigServiceImpl implements BoxAlarmConfigService {

    @Resource
    private BoxAlarmConfigMapper boxAlarmConfigMapper;

    @Override
    public List<BoxAlarmConfig> getAlarmByCodes(Set<String> codes) {
        return boxAlarmConfigMapper.getAlarmByCodes(codes);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return boxAlarmConfigMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(BoxAlarmConfig boxAlarmConfig) {
        Example example = new Example(BoxAlarmConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (StringUtils.isNotEmpty(boxAlarmConfig.getRuleCode())) {
            cri.andEqualTo("ruleCode", boxAlarmConfig.getRuleCode());
        }
        return boxAlarmConfigMapper.deleteByExample(example);
    }

    @Override
    public int insert(BoxAlarmConfig boxAlarmConfig) {
        return boxAlarmConfigMapper.insert(boxAlarmConfig);
    }

    @Override
    public int insertSelective(BoxAlarmConfig boxAlarmConfig) {
        return boxAlarmConfigMapper.insertSelective(boxAlarmConfig);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return boxAlarmConfigMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<BoxAlarmConfig> selectAll() {
        return boxAlarmConfigMapper.selectAll();
    }

    @Override
    public BoxAlarmConfig selectByPrimaryKey(Object o) {
        return boxAlarmConfigMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(BoxAlarmConfig boxAlarmConfig) {
        return boxAlarmConfigMapper.selectCount(boxAlarmConfig);
    }

    @Override
    public List<BoxAlarmConfig> select(BoxAlarmConfig boxAlarmConfig) {
        Example example = new Example(BoxAlarmConfig.class);
        if (StringUtils.isNotEmpty(boxAlarmConfig.getRuleCode())) {
            example.createCriteria().andEqualTo("ruleCode", boxAlarmConfig.getRuleCode());
        }
        return boxAlarmConfigMapper.selectByExample(example);
    }

    @Override
    public BoxAlarmConfig selectOne(BoxAlarmConfig boxAlarmConfig) {
        return boxAlarmConfigMapper.selectOne(boxAlarmConfig);
    }

    @Override
    public int updateByPrimaryKey(BoxAlarmConfig boxAlarmConfig) {
        return boxAlarmConfigMapper.updateByPrimaryKey(boxAlarmConfig);
    }

    @Override
    public int updateByPrimaryKeySelective(BoxAlarmConfig boxAlarmConfig) {
        return boxAlarmConfigMapper.updateByPrimaryKeySelective(boxAlarmConfig);
    }
}
