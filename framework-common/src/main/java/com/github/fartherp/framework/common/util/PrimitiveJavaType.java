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
package com.github.fartherp.framework.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA .
 * @author CK
 * @date 2016/5/31
 */
public enum PrimitiveJavaType {
	/**
	 * 基础类型枚举
	 */
    BYTE(Byte.class, "byte", "parseByte"),
    SHORT(Short.class, "short", "parseShort"),
    INTEGER(Integer.class, "int", "parseInt"),
    LONG(Long.class, "long", "parseLong"),
    BOOLEAN(Boolean.class, "boolean", "parseBoolean"),
    FLOAT(Float.class, "float", "parseFloat"),
    DOUBLE(Double.class, "double", "parseDouble");

    private final Class clazz;

	private final String lower;

	private final String method;

    public static final Map<String, PrimitiveJavaType> PRIMITIVE_JAVA_TYPE_MAP;

    PrimitiveJavaType(Class clazz, String lower, String method) {
        this.clazz = clazz;
        this.lower = lower;
        this.method = method;
    }

    static {
		PRIMITIVE_JAVA_TYPE_MAP = new HashMap<>();
        for (PrimitiveJavaType javaType : PrimitiveJavaType.values()) {
            PRIMITIVE_JAVA_TYPE_MAP.put(javaType.clazz.getSimpleName(), javaType);
            PRIMITIVE_JAVA_TYPE_MAP.put(javaType.lower, javaType);
            PRIMITIVE_JAVA_TYPE_MAP.put(javaType.clazz.getName(), javaType);
        }
    }

    @SuppressWarnings("all")
    public static Object covertValue(Class clazz, Object value) {
        if (value == null) {
            return null;
        }
        PrimitiveJavaType javaType = PRIMITIVE_JAVA_TYPE_MAP.get(clazz.getSimpleName());
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
        return PRIMITIVE_JAVA_TYPE_MAP.get(fullyQualifiedName);
    }

    public static PrimitiveJavaType getByShortName(String shortName) {
        return PRIMITIVE_JAVA_TYPE_MAP.get(shortName);
    }
}
