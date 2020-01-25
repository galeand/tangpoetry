package com.sse.tangpoetry.enums;

/**
 * @name: PoetryTypeEnum
 * @author: yf.xiang
 * @create: 2020-01-25 13:11
 * @description:
 */
public enum  PoetryTypeEnum {
    TANG_POETRY (1, "唐诗"),
    SONG_CI     (2, "宋词");

    private Integer code;
    private String desc;

    public static PoetryTypeEnum typeOf(Integer code){
        for(PoetryTypeEnum type: PoetryTypeEnum.values()){
            if (type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    PoetryTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
