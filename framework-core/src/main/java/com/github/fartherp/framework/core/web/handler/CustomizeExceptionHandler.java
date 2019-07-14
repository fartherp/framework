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
package com.github.fartherp.framework.core.web.handler;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理接口
 * @author CK
 * @date 2016/9/11
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
