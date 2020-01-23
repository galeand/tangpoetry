package com.sse.tangpoetry.bean;

/**
 * @name: SongCiEveryDto
 * @author: yf.xiang
 * @create: 2020-01-23 13:53
 * @description:
 */
public class SongCiEveryDto {
    /**
     * 段落
     */
    private String paragraphs;

    /**
     * 词牌名
     */
    private String rhythmic;

    /**
     * 词人
     */
    private String name;

    public String getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(String paragraphs) {
        this.paragraphs = paragraphs;
    }

    public String getRhythmic() {
        return rhythmic;
    }

    public void setRhythmic(String rhythmic) {
        this.rhythmic = rhythmic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
