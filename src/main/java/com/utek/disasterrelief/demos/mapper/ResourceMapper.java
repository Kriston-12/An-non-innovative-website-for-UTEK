package com.utek.disasterrelief.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.utek.disasterrelief.demos.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    @Select("SELECT * FROM resource ORDER BY RAND() LIMIT 1")
    Resource selectRandomResource();


}
