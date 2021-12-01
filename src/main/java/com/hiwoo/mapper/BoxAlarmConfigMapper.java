package com.hiwoo.mapper;

import com.hiwoo.entity.BoxAlarmConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;


@Repository
public interface BoxAlarmConfigMapper extends Mapper<BoxAlarmConfig> {

    List<BoxAlarmConfig> getAlarmByCodes(Set<String> codes);

}
