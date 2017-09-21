/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

/**
 * 公用拦截测试类
 * Auth: CK
 * Date: 2016/9/12
 */
public class TestMethodInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        if (targetClass != null) {
            System.out.println(targetClass.getSimpleName());
        }
        Object obj = invocation.proceed();
        if (obj != null) {
            System.out.println("-------" + obj.toString());
        }
        return obj;
    }
}
