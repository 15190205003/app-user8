package com.hiwoo.mapper;

import com.hiwoo.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User>{
    int getUserCountByTag(String tag);

    int deleteUserById(User user);
}
