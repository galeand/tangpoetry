package com.sse.tangpoetry.enums;

/**
 * @name: QueryTypeEnum
 * @author: yf.xiang
 * @create: 2020-01-23 14:21
 * @description:
 */
public enum QueryTypeEnum {
    TITLE   (1, "标题"),
    AUTHOR  (2, "作者"),
    CONTENT (3, "内容"),
    NICKNAME(4, "别称");

    private Integer id;
    private String desc;

    QueryTypeEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
