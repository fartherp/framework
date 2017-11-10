/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

import com.github.fartherp.framework.core.bean.ServiceLocator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;

import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/8
 */
public class BeanWrapperUtils {
    public static void setProperties(Object obj, Properties properties) {
        if (properties.keySet().isEmpty()) {
            return;
        }
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        for (Map.Entry entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (value instanceof String) {
                PropertyValue propertyValue = new PropertyValue(key, ServiceLocator.getInstance().getBean((String) value));
                mutablePropertyValues.addPropertyValue(propertyValue);
            } else {
                PropertyValue propertyValue = new PropertyValue(key, value);
                mutablePropertyValues.addPropertyValue(propertyValue);
            }
        }
        beanWrapper.setPropertyValues(mutablePropertyValues);
    }
}
