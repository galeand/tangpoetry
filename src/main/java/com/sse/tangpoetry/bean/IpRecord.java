package com.sse.tangpoetry.bean;

import com.sse.tangpoetry.enums.PoetryTypeEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * @name: IpRecord
 * @author: yf.xiang
 * @create: 2020-01-25 12:58
 * @description:
 */
public class IpRecord {

    /**
     * 访问关键字
     */
    private String key;

    /**
     * 访问request，记录ip信息
     */
    private HttpServletRequest request;

    /**
     * 查询诗词类型
     */
    private PoetryTypeEnum type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public PoetryTypeEnum getType() {
        return type;
    }

    public void setType(PoetryTypeEnum type) {
        this.type = type;
    }

    public IpRecord(String key, HttpServletRequest request, PoetryTypeEnum type) {
        this.key = key;
        this.request = request;
        this.type = type;
    }
}
