package com.sse.tangpoetry.service;

import java.util.List;

public interface PoetryService {
    /**
     *  返回的结果List里面，第一个是查找到的对象数量，第二个是从数据库查找到的内容
     * @param poetName
     * @return
     */
    List<Object> getPoetryByPoet(String poetName);

    List<Object> getPoetryByTitle(String title);

    List<Object> getPoetryByContent(String content);

}
