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
