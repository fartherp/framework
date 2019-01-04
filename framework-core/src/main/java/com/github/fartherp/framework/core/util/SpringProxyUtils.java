/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.AsyncExecutionInterceptor;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * spring代理操作类
 * Auth: CK
 * Date: 2016/9/11
 */
public class SpringProxyUtils {

    /**
     * 通过代理对象获取被代理对象
     *
     * @param proxy 代理对象
     * @param <T>   强转
     * @return 被代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRealTarget(Object proxy) {
        ConfigurablePropertyAccessor accessor;
        while (AopUtils.isAopProxy(proxy)) {
            ProxyFactory proxyFactory = null;
            if (AopUtils.isJdkDynamicProxy(proxy)) {
                proxyFactory = findJdkDynamicProxyFactory(proxy);
            }
            if (AopUtils.isCglibProxy(proxy)) {
                proxyFactory = findCglibProxyFactory(proxy);
            }
            accessor = PropertyAccessorFactory.forDirectFieldAccess(proxyFactory);
            TargetSource targetSource = (TargetSource) accessor.getPropertyValue("targetSource");
            try {
                proxy = targetSource.getTarget();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) proxy;
    }

    /**
     * 判断被代理的对象是否被双重代理
     *
     * @param proxy 被代理的对象
     */
    @SuppressWarnings("checked")
    public static boolean isMultipleProxy(Object proxy) {
        ConfigurablePropertyAccessor accessor;
        try {
            ProxyFactory proxyFactory = null;
            if (AopUtils.isJdkDynamicProxy(proxy)) {
                proxyFactory = findJdkDynamicProxyFactory(proxy);
            }
            if (AopUtils.isCglibProxy(proxy)) {
                proxyFactory = findCglibProxyFactory(proxy);
            }
            accessor = PropertyAccessorFactory.forDirectFieldAccess(proxyFactory);
            TargetSource targetSource = (TargetSource) accessor.getPropertyValue("targetSource");
            return AopUtils.isAopProxy(targetSource.getTarget());
        } catch (Exception e) {
            throw new IllegalArgumentException("proxy args maybe not proxy with cglib or jdk dynamic proxy. this method not support", e);
        }
    }

    /**
     * 获取被代理对象的工厂类
     *
     * @param proxy jdk类型的代理类
     */
    public static ProxyFactory findJdkDynamicProxyFactory(Object proxy) {
        InvocationHandler h = Proxy.getInvocationHandler(proxy);
        ConfigurablePropertyAccessor accessor;
        accessor = PropertyAccessorFactory.forDirectFieldAccess(h);
        return (ProxyFactory) accessor.getPropertyValue("advised");
    }

    /**
     * 获取代理对象对应的工厂实例
     *
     * @param proxy cglib类型的代理类
     */
    public static ProxyFactory findCglibProxyFactory(Object proxy) {
        ConfigurablePropertyAccessor accessor = PropertyAccessorFactory.forDirectFieldAccess(proxy);
        Object cglib$CALLBACK_0 = accessor.getPropertyValue("CGLIB$CALLBACK_0");
        accessor = PropertyAccessorFactory.forDirectFieldAccess(cglib$CALLBACK_0);
        return (ProxyFactory) accessor.getPropertyValue("advised");
    }

    /**
     * 查看指定的代理对象是否 添加事务切面
     * see http://jinnianshilongnian.iteye.com/blog/1850432
     *
     * @param proxy
     */
    public static boolean isTransactional(Object proxy) {
        return hasAdvice(proxy, TransactionInterceptor.class);
    }

    /**
     * 移除代理对象的异步调用支持
     */
    public static void removeTransactional(Object proxy) {
        removeAdvisor(proxy, TransactionInterceptor.class);
    }

    /**
     * 是否是异步的代理
     *
     * @param proxy
     */
    public static boolean isAsync(Object proxy) {
        return hasAdvice(proxy, AsyncExecutionInterceptor.class);
    }

    /**
     * 移除代理对象的异步调用支持
     */
    public static void removeAsync(Object proxy) {
        removeAdvisor(proxy, AsyncExecutionInterceptor.class);
    }

    private static void removeAdvisor(Object proxy, Class<? extends Advice> adviceClass) {
        if (!AopUtils.isAopProxy(proxy)) {
            return;
        }
        ProxyFactory proxyFactory = null;
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            proxyFactory = findJdkDynamicProxyFactory(proxy);
        }
        if (AopUtils.isCglibProxy(proxy)) {
            proxyFactory = findCglibProxyFactory(proxy);
        }

        if (proxyFactory == null) {
            return;
        }

        Advisor[] advisors = proxyFactory.getAdvisors();
        if (advisors.length == 0) {
            return;
        }

        for (Advisor advisor : advisors) {
            if (adviceClass.isAssignableFrom(advisor.getAdvice().getClass())) {
                proxyFactory.removeAdvisor(advisor);
                break;
            }
        }
    }

    /**
     * @param proxy       被代理类
     * @param adviceClass 判断代理类是否包含该类型的拦截器
     */

    private static boolean hasAdvice(Object proxy, Class<? extends Advice> adviceClass) {
        if (!AopUtils.isAopProxy(proxy)) {
            return false;
        }
        ProxyFactory proxyFactory = null;
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            proxyFactory = findJdkDynamicProxyFactory(proxy);
        }
        if (AopUtils.isCglibProxy(proxy)) {
            proxyFactory = findCglibProxyFactory(proxy);
        }

        if (proxyFactory == null) {
            return false;
        }

        Advisor[] advisors = proxyFactory.getAdvisors();
        if (advisors.length == 0) {
            return false;
        }

        for (Advisor advisor : advisors) {
            if (adviceClass.isAssignableFrom(advisor.getAdvice().getClass())) {
                return true;
            }
        }
        return false;
    }
}
