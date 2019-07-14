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
 * @author CK
 * @date 2017/11/8
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
                PropertyValue propertyValue = new PropertyValue(key, ServiceLocator.getBean((String) value));
                mutablePropertyValues.addPropertyValue(propertyValue);
            } else {
                PropertyValue propertyValue = new PropertyValue(key, value);
                mutablePropertyValues.addPropertyValue(propertyValue);
            }
        }
        beanWrapper.setPropertyValues(mutablePropertyValues);
    }
}
