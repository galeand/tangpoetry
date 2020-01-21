package com.sse.tangpoetry.controller;

/**
 * @name: BaseController
 * @author: yf.xiang
 * @create: 2020-01-20 18:42
 * @description:
 */

public interface BaseController {

    default void checkParams(Object params){}

    default void beforeExecute(){}

    default void handExecute(){}
}
