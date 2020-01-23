package com.sse.tangpoetry.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @name: SongCi
 * @author: yf.xiang
 * @create: 2020-01-22 19:00
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongCi {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 词人名
     */
    private String author;

    /**
     * 词句段落，词内容
     */
    private String paragraphs;

    /**
     * 词牌名
     */
    private String rhythmic;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "SongCi{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", paragraphs='" + paragraphs + '\'' +
                ", rhythmic='" + rhythmic + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
