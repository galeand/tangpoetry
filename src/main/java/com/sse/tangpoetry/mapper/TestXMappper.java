package com.sse.tangpoetry.mapper;

import com.sse.tangpoetry.bean.Poet;
import com.sse.tangpoetry.bean.TestX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestXMappper {
    @Select("select * from poets where name=#{name}")
    @Results(id = "poetMap", value = {@Result(column = "id", property = "x1"),
            @Result(column = "name", property = "x2"),
            @Result(column = "created_at", property = "x3"),
            @Result(column = "updated_at", property = "x4")})
    TestX getPoetByName(String name);

    @Select("select * from poets where id=#{poet_id}")
    TestX getPoetByPoet_id(Integer poet_id);
}
