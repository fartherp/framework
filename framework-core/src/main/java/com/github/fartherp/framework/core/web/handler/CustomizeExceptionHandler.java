/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.handler;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理接口
 * Auth: CK
 * Date: 2016/9/11
 */
public interface CustomizeExceptionHandler {

    /**
     *
     * @param ex 异常
     * @return true支持,false:不支持
     */
    boolean support(Exception ex, Class clazz);

    /**
     * 具体业务处理
     * @param request 请求
     * @param response 响应
     * @param handler 业务方法
     * @param ex 异常
     * @param resolver 具体处理类
     * @return 页面或null
     */
    ModelAndView deal(HttpServletRequest request, HttpServletResponse response,
                      Object handler, Exception ex, CustomizeExceptionHandlerResolver resolver);
}
