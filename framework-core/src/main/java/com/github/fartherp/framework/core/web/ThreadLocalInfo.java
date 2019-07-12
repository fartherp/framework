/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.core.web;

import org.springframework.core.NamedThreadLocal;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/17
 * @see org.springframework.web.context.request.RequestContextHolder
 */
public class ThreadLocalInfo {
    private static final ThreadLocal<ServletRequest> servletRequestThreadLocal =
            new NamedThreadLocal<ServletRequest>("ServletRequest");
    private static final ThreadLocal<ServletResponse> servletResponseThreadLocal =
            new NamedThreadLocal<ServletResponse>("ServletResponse");

    public static void resetServletRequest() {
        servletRequestThreadLocal.remove();
    }

    public static void resetServletResponse() {
        servletResponseThreadLocal.remove();
    }

    public static void reset() {
        resetServletRequest();
        resetServletResponse();
    }

    /**
     * 请求绑定到当前线程
     * @param servletRequest 请求
     */
    public static void setServletRequest(ServletRequest servletRequest) {
        servletRequestThreadLocal.set(servletRequest);
    }

    /**
     * 响应绑定到当前线程
     * @param servletResponse 响应
     */
    public static void setServletResponse(ServletResponse servletResponse) {
        servletResponseThreadLocal.set(servletResponse);
    }

    /**
     * 请求、响应绑定到当前线程
     * @param servletRequest 请求
     * @param servletResponse 响应
     */
    public static void set(ServletRequest servletRequest, ServletResponse servletResponse) {
        reset();
        setServletRequest(servletRequest);
        setServletResponse(servletResponse);
    }

    /**
     * 获取当前线程绑定的请求
     * @return 请求
     */
    public static HttpServletRequest currentHttpServletRequest() {
        return (HttpServletRequest) servletRequestThreadLocal.get();
    }

    /**
     * 获取当前线程绑定的响应
     * @return 响应
     */
    public static HttpServletResponse currentHttpServletResponse() {
        return (HttpServletResponse) servletResponseThreadLocal.get();
    }

    /**
     * 获取当前线程的Session
     * @return Session
     */
    public static HttpSession currentHttpServletSession() {
        return currentHttpServletSession(true);
    }

    /**
     * 获取当前线程的Session
     * @param flag
     *          true:存在-返回session对象，不存在-返回新建session对象
     *          false:存在-返回session对象，不存在-返回null
     * @return Session
     */
    public static HttpSession currentHttpServletSession(boolean flag) {
        return currentHttpServletRequest().getSession(flag);
    }
}
