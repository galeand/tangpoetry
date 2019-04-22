package com.sse.tangpoetry.mapper;

import com.sse.tangpoetry.bean.Poet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PoetMapper {

    @Select("select * from poets where name=#{name}")
    Poet getPoetByName(String name);
    @Select("select * from poets where id=#{poet_id}")
    Poet getPoetByPoet_id(Integer poet_id);
}
