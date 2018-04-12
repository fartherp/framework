/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/4
 */
public class MockInterceptor implements MethodInterceptor, Serializable, ApplicationContextAware {

    private boolean mock;

    private ApplicationContext applicationContext;

    public void setMock(boolean mock) {
        this.mock = mock;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (mock) {
            Class<?> targetClass = AopUtils.getTargetClass(invocation.getThis());
            Class<?>[] interfaces = targetClass.getInterfaces();
            Map<String, ?> map = applicationContext.getBeansOfType(interfaces[0]);
            for (Map.Entry<String, ?> m : map.entrySet()) {
                try {
                    // 拦截把自己除去
                    if (m.getValue().equals(invocation.getThis())) {
                        continue;
                    }
                    return ReflectionUtils.invokeMethod(invocation.getMethod(), m.getValue(), invocation.getArguments());
                } catch (Throwable e) {
                    // do nothing
                }
            }
        }
        return invocation.proceed();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
