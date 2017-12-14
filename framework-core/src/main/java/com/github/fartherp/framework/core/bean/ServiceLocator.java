/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 包装spring上下文信息ApplicationContext
 * Author: CK
 * Date: 2015/6/24
 */
public class ServiceLocator implements ApplicationContextAware {

    private static ServiceLocator locator = null;

    private static ApplicationContext factory = null;

    public ApplicationContext getFactory() {
        Assert.notNull(factory, "没有注入spring factory");
        return factory;
    }

    public void setFactory(ApplicationContext context) {
        setToFactory(context);
    }

    private static void setToFactory(ApplicationContext context) {
        factory = context;
    }

    public static ServiceLocator getInstance() {
        if (locator == null) {
            locator = new ServiceLocator();
        }
        return locator;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName) {
        return (T) getFactory().getBean(beanName);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getFactory().getBeansOfType(type);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return getFactory().getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        factory = applicationContext;
    }
}
