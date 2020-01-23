package com.sse.tangpoetry.mapper;

import com.sse.tangpoetry.bean.SongCi;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @name: SongCiMapper
 * @author: yf.xiang
 * @create: 2020-01-22 19:11
 * @description:
 */
@Mapper
public interface SongCiMapper {

    @Insert("INSERT INTO `tang_poetry`.`song_ci`(`id`,`author`, `paragraphs`, `rhythmic`, `created_at`, `updated_at`) VALUES (#{one.id},#{one.author},#{one.paragraphs},#{one.rhythmic},#{one.createdAt},#{one.updatedAt})")
    @Results(id = "songCiMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "author", property = "author"),
            @Result(column = "paragraphs", property = "paragraphs"),
            @Result(column = "rhythmic", property = "rhythmic"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt")})
    boolean insertOneRecord(@Param("one") SongCi record);

    @Select("SELECT * FROM song_ci WHERE rhythmic like concat('%',#{title},'%')")
    List<SongCi> getSongCiByTitle(String title);

    @Select("SELECT * FROM song_ci WHERE author like concat('%',#{author},'%')")
    List<SongCi> getSongCiByAuthor(String author);

    @Select("SELECT * FROM song_ci WHERE paragraphs like concat('%',#{content},'%')")
    List<SongCi> getSongCiByContent(String content);
}
