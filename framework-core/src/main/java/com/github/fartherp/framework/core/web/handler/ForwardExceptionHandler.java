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

import com.github.fartherp.framework.exception.web.ForwardException;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回错误页面
 * @author CK
 * @date 2016/9/9
 */
public class ForwardExceptionHandler implements CustomizeExceptionHandler {

    @Override
	public boolean support(Exception ex, Class clazz) {
        return ex instanceof ForwardException
                || ModelAndView.class.isAssignableFrom(clazz)
                || ModelMap.class.isAssignableFrom(clazz);
    }

    @Override
	public ModelAndView deal(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex, CustomizeExceptionHandlerResolver resolver) {
        // 把漏网的异常信息记入日志
        Map<String, Object> map = new HashMap<String, Object>(1);
        String message = ex.getMessage();
        if (message == null) {
            Throwable e = ex.getCause();
            if (e == null) {
                message = resolver.getMessage(ex);
            } else {
                message = resolver.getMessage(e);
            }
        }
        // 将错误信息传递给view
        map.put("errorMsg", message);
        return new ModelAndView("error", map);
    }
}
