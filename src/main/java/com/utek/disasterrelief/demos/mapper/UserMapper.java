package com.utek.disasterrelief.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.utek.disasterrelief.demos.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
