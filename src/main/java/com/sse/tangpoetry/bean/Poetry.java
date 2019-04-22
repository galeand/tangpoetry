package com.sse.tangpoetry.bean;

import java.util.Date;

//古诗的实体类
public class Poetry {
    private Integer id;
    private Integer poet_id;
    private String content;
    private String title;
    private Date created_at;
    private Date updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoet_id() {
        return poet_id;
    }

    public void setPoet_id(Integer poet_id) {
        this.poet_id = poet_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Poetry{" +
                "id=" + id +
                ", poet_id=" + poet_id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
