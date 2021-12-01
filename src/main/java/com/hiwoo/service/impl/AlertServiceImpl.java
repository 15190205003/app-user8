package com.hiwoo.service.impl;

import com.hiwoo.entity.Alert;
import com.hiwoo.mapper.AlertMapper;
import com.hiwoo.service.AlertService;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    @Resource
    AlertMapper alertMapper;

    @Override
    public int deleteByPrimaryKey(Object o) {
        return alertMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(Alert alert) {
        return alertMapper.delete(alert);
    }

    @Override
    public int insert(Alert alert) {
        return alertMapper.insert(alert);
    }

    @Override
    public int insertSelective(Alert alert) {
        return alertMapper.insertSelective(alert);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return alertMapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<Alert> selectAll() {
        return alertMapper.selectAll();
    }

    @Override
    public Alert selectByPrimaryKey(Object o) {
        return alertMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(Alert alert) {
        return alertMapper.selectCount(alert);
    }

    @Override
    public List<Alert> select(Alert alert) {
        Example example = new Example(Alert.class);
        Example.Criteria cri = example.createCriteria();
        if (null != alert.getRole() && 0 < alert.getRole()) {
            cri.andEqualTo("role",alert.getRole());
        }
        return alertMapper.selectByExample(example);
    }

    @Override
    public Alert selectOne(Alert alert) {
        return alertMapper.selectOne(alert);
    }

    @Override
    public int updateByPrimaryKey(Alert alert) {
        return alertMapper.updateByPrimaryKey(alert);
    }

    @Override
    public int updateByPrimaryKeySelective(Alert alert) {
        return alertMapper.updateByPrimaryKeySelective(alert);
    }
}
