/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.handler;

import com.github.fartherp.framework.exception.BaseException;
import com.github.fartherp.framework.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 公共异常拦截
 * <pre>
 *  &lt;bean id="responseExceptionHandler" class="cn.vansky.framework.core.web.handler.ResponseExceptionHandler"/&gt;
 *  &lt;bean id="forwardExceptionHandler" class="cn.vansky.framework.core.web.handler.ForwardExceptionHandler"/&gt;
 *  &lt;bean id="generalExceptionHandler" class="cn.vansky.framework.core.web.handler.GeneralExceptionHandler"/&gt;
 *  &lt;bean id="name" class="cn.vansky.framework.core.web.handler.CustomizeExceptionHandlerResolver"&gt;
 *      &lt;property name="exceptions"&gt;
 *          &lt;list&gt;
 *              &lt;ref bean="responseExceptionHandler"/&gt;
 *              &lt;ref bean="forwardExceptionHandler"/&gt;
 *              &lt;ref bean="generalExceptionHandler"/&gt;
 *          &lt;/list&gt;
 *      &lt;/property&gt;
 *  &lt;/bean&gt;
 * </pre>
 * Author: CK
 * Date: 2016/2/5
 */
public class CustomizeExceptionHandlerResolver implements HandlerExceptionResolver, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(CustomizeExceptionHandlerResolver.class);

    private List<CustomizeExceptionHandler> exceptions = new ArrayList<CustomizeExceptionHandler>();

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        logger.error("Catch Exception: ", ex);
        Method method = ((HandlerMethod) handler).getMethod();
        Class clazz = method.getReturnType();
        for (CustomizeExceptionHandler exceptionHandler : exceptions) {
            if (exceptionHandler.support(ex, clazz)) {
                return exceptionHandler.deal(request, response, handler, ex, this);
            }
        }
        return null;
    }

    public String getMessage(Throwable ex) {
        BaseException exception = new SystemException(ex);
        String message;
        if (ex instanceof DataIntegrityViolationException) {
            message = exception.getMessage(ex.getCause());
        } else {
            message = exception.getMessage();
        }
        if (null == message) {
            return "系统内部异常";
        }
        return message;
    }

    public void setExceptions(List<CustomizeExceptionHandler> exceptions) {
        this.exceptions = exceptions;
    }

    public void afterPropertiesSet() throws Exception {
        exceptions.add(new ResponseExceptionHandler());
        exceptions.add(new ForwardExceptionHandler());
        exceptions.add(new GeneralExceptionHandler());
    }
}
