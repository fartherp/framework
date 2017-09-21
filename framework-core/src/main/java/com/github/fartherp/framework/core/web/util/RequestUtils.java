/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/12
 */
public class RequestUtils {

    /**
     * 根据request获取contextpath
     * @param request req
     * @return 返回context path
     */
    public static String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    /**
     * 获取访问的path
     *
     * @param request 请求
     * @return 访问的path
     */
    public static String getReqPath(HttpServletRequest request) {
        String path = request.getRequestURI();

        // 获取contextpath
        String contextPath = getContextPath(request);

        if (path.indexOf(contextPath) == 0) {
            path = path.substring(contextPath.length());
        }
        return path;
    }
}
