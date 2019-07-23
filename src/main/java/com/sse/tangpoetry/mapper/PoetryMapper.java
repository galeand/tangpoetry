package com.sse.tangpoetry.mapper;


import com.sse.tangpoetry.bean.Poetry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PoetryMapper {

    @Select("select * from poetries where title like CONCAT('%',#{title},'%')")
    List<Poetry> getPoetryLiketitle(String title);

//    @Select("select * from poetries where title=#{title}")
//    Poetry getPoetryByTitle(String title);

    @Select("select * from poetries where content like CONCAT('%',#{content},'%')")
    List<Poetry> getPoetryLikeContent(String content);

    @Select("select * from poetries where poet_id=#{poet_id}")
    List<Poetry> getPeotryByPoet_id(Integer poet_id);

    @Select("select * from poetries")
    List<Poetry> getAllPoetry(String title);

}
