package com.hiwoo.service.impl;

import com.hiwoo.entity.Individuation;
import com.hiwoo.mapper.IndividuationMapper;
import com.hiwoo.service.IndividuationService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndividuationServiceImpl implements IndividuationService {

    @Resource
    IndividuationMapper individuationMapper;

    @Override
    public int deleteByPrimaryKey(Object o) {
        return individuationMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(Individuation individuation) {
        return individuationMapper.delete(individuation);
    }

    @Override
    public int insert(Individuation individuation) {
        return individuationMapper.insert(individuation);
    }

    @Override
    public int insertSelective(Individuation individuation) {
        return individuationMapper.insertSelective(individuation);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return individuationMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<Individuation> selectAll() {
        return individuationMapper.selectAll();
    }

    @Override
    public Individuation selectByPrimaryKey(Object o) {
        return individuationMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(Individuation individuation) {
        return individuationMapper.selectCount(individuation);
    }

    @Override
    public List<Individuation> select(Individuation individuation) {
        Example example = new Example(Individuation.class);
        example.createCriteria().andEqualTo("appId", individuation.getAppId());
        return individuationMapper.selectByExample(example);
    }

    @Override
    public Individuation selectOne(Individuation individuation) {
        return individuationMapper.selectOne(individuation);
    }

    @Override
    public int updateByPrimaryKey(Individuation individuation) {
        return individuationMapper.updateByPrimaryKey(individuation);
    }

    @Override
    public int updateByPrimaryKeySelective(Individuation individuation) {
        return individuationMapper.updateByPrimaryKeySelective(individuation);
    }
}
