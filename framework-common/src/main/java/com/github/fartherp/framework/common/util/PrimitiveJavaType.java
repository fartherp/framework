/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/5/31
 */
public enum PrimitiveJavaType {
    BYTE(Byte.class, "byte", "parseByte"),
    SHORT(Short.class, "short", "parseShort"),
    INTEGER(Integer.class, "int", "parseInt"),
    LONG(Long.class, "long", "parseLong"),
    BOOLEAN(Boolean.class, "boolean", "parseBoolean"),
    FLOAT(Float.class, "float", "parseFloat"),
    DOUBLE(Double.class, "double", "parseDouble"),
    ;

    public final Class clazz;

    public final String lower;

    public final String method;

    public final static Map<String, PrimitiveJavaType> map = new HashMap<>();

    PrimitiveJavaType(Class clazz, String lower, String method) {
        this.clazz = clazz;
        this.lower = lower;
        this.method = method;
    }

    static {
        for (PrimitiveJavaType javaType : PrimitiveJavaType.values()) {
            map.put(javaType.clazz.getSimpleName(), javaType);
            map.put(javaType.lower, javaType);
            map.put(javaType.clazz.getName(), javaType);
        }
    }

    @SuppressWarnings("all")
    public static Object covertValue(Class clazz, Object value) {
        if (value == null) {
            return null;
        }
        PrimitiveJavaType javaType = map.get(clazz.getSimpleName());
        try {
            if (javaType != null) {
                return javaType.clazz.getMethod(javaType.method, String.class).invoke(null, value.toString());
            } else {
                return value;
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("no found method: " + javaType.method, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrimitiveJavaType getByFullyQualifiedName(String fullyQualifiedName) {
        return map.get(fullyQualifiedName);
    }

    public static PrimitiveJavaType getByShortName(String shortName) {
        return map.get(shortName);
    }
}
