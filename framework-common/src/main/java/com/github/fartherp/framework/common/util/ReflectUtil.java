/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射工具类
 * Author: CK
 * Date: 2015/8/8
 */
public class ReflectUtil {

    /**
     * 获取类属性列表
     * @param obj obj
     * @return 类属性列表
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Object obj) {
        try {
            // 获取类属性
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            return beanInfo.getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException("Could not get '" + obj.getClass().getName()
                    + "' class property", e);
        }
    }

    /**
     * 获取obj对象fieldName的Field
     *
     * @param obj 对象
     * @param fieldName 字段名
     * @return 字段
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        Class<?> searchType = obj.getClass();
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * 静态方法反射
     * @param searchType class类型
     * @param fieldName 字段名
     * @return 字段
     */
    public static Field getFieldByFieldName(Class<?> searchType, String fieldName) {
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * 获取字段对应的值
     * @param obj 对象
     * @param field 字段{@link Field}
     * @return value
     */
    public static Object getValueByFieldName(Object obj, Field field) {
        Object value = null;
        if (field != null) {
            try {
                if (field.isAccessible()) {
                    value = field.get(obj);
                } else {
                    field.setAccessible(true);
                    value = field.get(obj);
                    field.setAccessible(false);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 获取obj对象fieldName的属性值
     * @param obj 对象
     * @param fieldName 字段名
     * @return 属性值
     */
    public static Object getValueByFieldName(Object obj, String fieldName) {
        PropertyDescriptor[] ts = getPropertyDescriptors(obj);
        for (PropertyDescriptor t : ts) {
            if (t.getName().equals(fieldName)) {
                Method r = t.getReadMethod();
                try {
                    Class clazz = r.getDeclaringClass();
                    if (!Modifier.isPublic(clazz.getModifiers())) {
                        r.setAccessible(true);
                    }
                    return r.invoke(obj);
                }
                catch (Throwable e) {
                    throw new RuntimeException("Could not get property '" + t.getName(), e);
                }
            }
        }
        return null;
    }

    /**
     * 设置obj对象fieldName的属性值
     *
     * @param obj 对象
     * @param fieldName 字段名
     * @param value 字段
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value) {
        try {
            Field field = getFieldByFieldName(obj, fieldName);
            if (field.isAccessible()) {
                field.set(obj, value);
            } else {
                field.setAccessible(true);
                field.set(obj, value);
                field.setAccessible(false);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not set property '" + fieldName, e);
        }
    }

    public static void setValueByField(Object obj, String fieldName, Object value) {
        try {
            if (value != null) {
                Field field = getFieldByFieldName(obj, fieldName);
                Object tmp = PrimitiveJavaType.covertValue(field.getType(), value);
                if (field.isAccessible()) {
                    field.set(obj, tmp);
                } else {
                    field.setAccessible(true);
                    field.set(obj, tmp);
                    field.setAccessible(false);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not set property '" + fieldName, e);
        }
    }
}
