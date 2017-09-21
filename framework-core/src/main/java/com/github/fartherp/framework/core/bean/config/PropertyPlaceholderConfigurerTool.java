/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.bean.config;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class for {@link PropertyPlaceholderConfigurer}
 * Auth: CK
 * Date: 2016/8/29
 */
public final class PropertyPlaceholderConfigurerTool {

    /**
     * get {@link Properties} instance from  {@link ConfigurableListableBeanFactory}
     *
     * @param beanFactory spring container
     * @return {@link Properties} instance
     */
    public static Properties getRegisteredPropertyResourceConfigurer(ConfigurableListableBeanFactory beanFactory) {
        Class<PropertyPlaceholderConfigurer> clazz = PropertyPlaceholderConfigurer.class;
        Map<String, PropertyPlaceholderConfigurer> beans = beanFactory.getBeansOfType(clazz);
        if (MapUtils.isEmpty(beans)) {
            return null;
        }

        PropertyPlaceholderConfigurer config = beans.entrySet().iterator().next().getValue();
        Method m = ReflectionUtils.findMethod(clazz, "mergeProperties");
        m.setAccessible(true);
        return (Properties) ReflectionUtils.invokeMethod(m, config);
    }

    /**
     * To create placeholder parser
     *
     * @param propertyResource {@link Properties} instance
     * @return {@link PlaceholderResolver} instance
     */
    public static PlaceholderResolver createPlaceholderParser(final Properties propertyResource) {
        if (propertyResource == null) {
            return null;
        }
        PlaceholderResolver resolver = new PlaceholderResolver(
                new PlaceholderResolved() {
                    public String doResolved(String placeholder) {
                        return propertyResource.getProperty(placeholder);
                    }
                });
        return resolver;
    }
}
