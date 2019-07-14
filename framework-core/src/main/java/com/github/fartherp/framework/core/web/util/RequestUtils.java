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
package com.github.fartherp.framework.core.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/8/12
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
