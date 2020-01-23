package com.sse.tangpoetry.mapper;

import com.sse.tangpoetry.bean.SongCi;
import com.sse.tangpoetry.bean.SongCiAuthor;
import org.apache.ibatis.annotations.*;

/**
 * @name: SongCiAuthorMapper
 * @author: yf.xiang
 * @create: 2020-01-23 12:24
 * @description:
 */
@Mapper
public interface SongCiAuthorMapper {

    @Insert("INSERT INTO `tang_poetry`.`song_ci_author`(`id`,`name`, `description`, `created_at`, `updated_at`) VALUES (#{one.id},#{one.name},#{one.description},#{one.createdAt},#{one.updatedAt})")
    @Results(id = "map", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt")})
    boolean insertOneRecord(@Param("one") SongCiAuthor record);

//    @ResultMap(value = "map")
    @Results(id = "map", value = {
        @Result(column = "id", property = "id"),
        @Result(column = "name", property = "name"),
        @Result(column = "description", property = "description"),
        @Result(column = "created_at", property = "createdAt"),
        @Result(column = "updated_at", property = "updatedAt")})
    @Select("SELECT * FROM song_ci_author WHERE name like concat('%',#{name},'%')")
    SongCiAuthor getSongCiAuthorByName(String name);

    @Select("SELECT * FROM song_ci_author WHERE description like concat('%',#{name},'%')")
    SongCiAuthor getSongCiAuthorByDesc(String key);

}
