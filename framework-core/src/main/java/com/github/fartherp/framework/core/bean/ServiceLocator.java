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

    private static ApplicationContext factory = null;

    public static ApplicationContext getFactory() {
        Assert.notNull(factory, "没有注入spring factory");
        return factory;
    }

    public static void setFactory(ApplicationContext context) {
        factory = context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) getFactory().getBean(beanName);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getFactory().getBeansOfType(type);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return getFactory().getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clazz 类型
     * @return Object
     *
     */
    public static <T> T getBean(Class<T> clazz) {
        return getFactory().getBean(clazz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name 名称
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return getFactory().containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name 名称
     * @return boolean
     *
     */
    public static boolean isSingleton(String name) {
        return getFactory().isSingleton(name);
    }

    /**
     *
     * @param name 名称
     * @return Class 注册对象的类型
     */
    public static Class<?> getType(String name) {
        return getFactory().getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name 名称
     * @return String[]
     */
    public static String[] getAliases(String name) {
        return getFactory().getAliases(name);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setFactory(applicationContext);
    }
}
