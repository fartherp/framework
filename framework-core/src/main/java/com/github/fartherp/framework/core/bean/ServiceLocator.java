/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.bean;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * 包装spring上下文信息ApplicationContext
 * Author: CK
 * Date: 2015/6/24
 */
public class ServiceLocator {
    private static final Logger LOGGER = Logger.getLogger(ServiceLocator.class);

    private static ServiceLocator locator = null;

    private static ApplicationContext factory = null;

    public ApplicationContext getFactory() {
        if (factory == null) {
            LOGGER.error("ServiceLocator.factory is null maybe not config InitSystemListener correctly in web.xml");
        }

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
}
