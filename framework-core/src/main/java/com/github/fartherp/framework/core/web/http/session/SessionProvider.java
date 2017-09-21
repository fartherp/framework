/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.http.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Session提供者
 * Author: CK
 * Date:2014/10/6
 */
public interface SessionProvider {
    /**
     * 获取session属性
     * @param request 请求
     * @param name session中的KEY
     * @return session中KEY中对应的VALUE
     */
	public Serializable getAttribute(HttpServletRequest request, String name);

    /**
     * 获取session属性
     * @param name session中的KEY
     * @return session中KEY中对应的VALUE
     */
    public Serializable getAttribute(String name);

    /**
     * 设置session属性
     * @param request 请求
     * @param response 响应
     * @param name session中的KEY
     * @param value session中的VALUE
     */
	public void setAttribute(HttpServletRequest request, HttpServletResponse response,
                             String name, Serializable value);

    /**
     * 设置session属性
     * @param name session中的KEY
     * @param value session中的VALUE
     */
    public void setAttribute(String name, Serializable value);

    /**
     * 设置session属性
     * @param name session中的KEY
     * @param value session中的VALUE
     */
    public void setAttribute(String name, Object value);

    /**
     * 获取session的ID
     * @param request 请求
     * @param response 响应
     * @return session的ID
     */
	public String getSessionId(HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取session的ID
     * @return session的ID
     */
    public String getSessionId();

    /**
     * sessions注销
     * @param request 请求
     * @param response 响应
     */
	public void logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * sessions注销
     */
    public void logout();
}
