package com.hiwoo.service.impl;

import com.hiwoo.entity.BoxEventConfig;
import com.hiwoo.mapper.BoxEventConfigMapper;
import com.hiwoo.service.BoxEventConfigService;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BoxEventConfigServiceImpl implements BoxEventConfigService {


    @Resource
    private BoxEventConfigMapper boxEventConfigMapper;

    @Override
    public List<BoxEventConfig> getEventByCodes(Set<String> codes) {
        return boxEventConfigMapper.getEventByCodes(codes);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return boxEventConfigMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(BoxEventConfig boxEventConfig) {

        Example example = new Example(BoxEventConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (StringUtils.isNotEmpty(boxEventConfig.getRuleCode())) {
            cri.andEqualTo("ruleCode", boxEventConfig.getRuleCode());
        }
        return boxEventConfigMapper.deleteByExample(example);
    }

    @Override
    public int insert(BoxEventConfig boxEventConfig) {
        return boxEventConfigMapper.insert(boxEventConfig);
    }

    @Override
    public int insertSelective(BoxEventConfig boxEventConfig) {
        return boxEventConfigMapper.insertSelective(boxEventConfig);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return boxEventConfigMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<BoxEventConfig> selectAll() {
        return boxEventConfigMapper.selectAll();
    }

    @Override
    public BoxEventConfig selectByPrimaryKey(Object o) {
        return boxEventConfigMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(BoxEventConfig boxEventConfig) {
        return boxEventConfigMapper.selectCount(boxEventConfig);
    }

    @Override
    public List<BoxEventConfig> select(BoxEventConfig boxEventConfig) {
        Example example = new Example(BoxEventConfig.class);
        Example.Criteria cri = example.createCriteria();
        if (StringUtils.isNotEmpty(boxEventConfig.getRuleCode())) {
            cri.andEqualTo("ruleCode", boxEventConfig.getRuleCode());
        }
        return boxEventConfigMapper.selectByExample(example);
    }

    @Override
    public BoxEventConfig selectOne(BoxEventConfig boxEventConfig) {
        return boxEventConfigMapper.selectOne(boxEventConfig);
    }

    @Override
    public int updateByPrimaryKey(BoxEventConfig boxEventConfig) {
        return boxEventConfigMapper.updateByPrimaryKey(boxEventConfig);
    }

    @Override
    public int updateByPrimaryKeySelective(BoxEventConfig boxEventConfig) {
        return boxEventConfigMapper.updateByPrimaryKeySelective(boxEventConfig);
    }
}
