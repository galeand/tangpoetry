package com.sse.tangpoetry.mapper;

import com.sse.tangpoetry.bean.UserAccessTable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @name: UserAccessTableMapper
 * @author: yf.xiang
 * @create: 2020-01-25 13:29
 * @description:
 */
public interface UserAccessTableMapper {
    @Results(id = "test1", value = {@Result(column = "id", property = "id"),
            @Result(column = "ip", property = "ip"),
            @Result(column = "ip_real_address", property = "ipRealAddress"),
            @Result(column = "address_json", property = "addressJson"),
            @Result(column = "access_time", property = "accessTime"),
            @Result(column = "key_word", property = "keyWord"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "possible_person_name", property = "possiblePersonName"),
            @Result(column = "user_agent", property = "userAgent"),
            @Result(column = "device", property = "device"),
            @Result(column = "os", property = "os")
    })
    @Insert("INSERT INTO `tang_poetry`.`user_access`(`ip`, `ip_real_address`, `address_json`, `access_time`, `key_word`, `created_at`, `possible_person_name`, `user_agent`, `device`, `os`, `type`) " +
            "VALUES (#{ip}, #{ipRealAddress}, #{addressJson}, #{accessTime}, #{keyWord}, #{createdAt}, #{possiblePersonName}, #{userAgent}, #{device}, #{os}), #{type})")
    Boolean insert(@Param("ip") String ip,
                   @Param("ipRealAddress") String ipRealAddress,
                   @Param("addressJson") String addressJson,
                   @Param("accessTime") String accessTime,
                   @Param("keyWord") String keyWord,
                   @Param("createdAt") String createdAt,
                   @Param("possiblePersonName") String possiblePersonName,
                   @Param("userAgent") String userAgent,
                   @Param("device") String device,
                   @Param("os") String os,
                   @Param("type")String type);

    /**
     * 查询最近的5条访问记录
     * @return
     */
//    @ResultType(value = "com.sse.kaizhong.bean.UserAccessTable")
//    @ResultMap(value = "test1")
    @Results(id = "", value = {@Result(column = "id", property = "id"),
            @Result(column = "ip", property = "ip"),
            @Result(column = "ip_real_address", property = "ipRealAddress"),
            @Result(column = "address_json", property = "addressJson"),
            @Result(column = "access_time", property = "accessTime"),
            @Result(column = "key_word", property = "keyWord"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "possible_person_name", property = "possiblePersonName"),
            @Result(column = "user_agent", property = "userAgent"),
            @Result(column = "device", property = "device"),
            @Result(column = "os", property = "os")
    })
    @Select("SELECT ip,ip_real_address,created_at,key_word,device,os,possible_person_name FROM user_access ORDER BY created_at DESC LIMIT 5;")
    List<UserAccessTable> selectLately();

    /**
     * 查询所有的ip访问记录
     * @return
     */
//    @ResultMap(value = "test1")
    @Results(id = "", value = {@Result(column = "id", property = "id"),
            @Result(column = "ip", property = "ip"),
            @Result(column = "ip_real_address", property = "ipRealAddress"),
            @Result(column = "address_json", property = "addressJson"),
            @Result(column = "access_time", property = "accessTime"),
            @Result(column = "key_word", property = "keyWord"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "possible_person_name", property = "possiblePersonName"),
            @Result(column = "user_agent", property = "userAgent"),
            @Result(column = "device", property = "device"),
            @Result(column = "os", property = "os")
    })
    @Select("SELECT ip,ip_real_address,created_at,key_word,device,os,possible_person_name FROM user_access ORDER BY created_at DESC;")
    List<UserAccessTable> selectAll();
}
