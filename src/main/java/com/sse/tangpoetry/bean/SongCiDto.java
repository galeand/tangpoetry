package com.sse.tangpoetry.bean;

import com.sse.tangpoetry.enums.QueryTypeEnum;

import java.util.List;

/**
 * @name: SongCiDto
 * @author: yf.xiang
 * @create: 2020-01-23 13:41
 * @description:
 */
public class SongCiDto {

    /**
     * 词人
     */
    private String author;

    /**
     * 词人简介
     */
    private String intro;

    /**
     * 词人所做所有宋词
     */
    private List<SongCiEveryDto> songCiList;

    /**
     * @see com.sse.tangpoetry.enums.QueryTypeEnum
     */
    private QueryTypeEnum type;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<SongCiEveryDto> getSongCiList() {
        return songCiList;
    }

    public void setSongCiList(List<SongCiEveryDto> songCiList) {
        this.songCiList = songCiList;
    }

    public QueryTypeEnum getType() {
        return type;
    }

    public void setType(QueryTypeEnum type) {
        this.type = type;
    }
}
