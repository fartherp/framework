/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeEnv;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化工具类（基于 Protostuff 实现）
 */
public class ProtobufSerializeUtil {

    private final static Map<String, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private final static Objenesis objenesis = new ObjenesisStd(true);

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(ProtobufSerializeUtil.class);

    /**
     * 序列化（对象 -> 字节数组），序列化所有属性字段.
     */
    public static <T> byte[] serialize(T obj) {
        return serialization(obj, null, null);
    }

    /**
     * 序列化（对象 -> 字节数组），只序列化对应的属性字段.
     */
    public static <T> byte[] serialize(T obj, String[] inclusives) {
        return serialization(obj, null, inclusives);
    }

    /**
     * 序列化（对象 -> 字节数组），排除对应的属性字段.
     */
    public static <T> byte[] serializeWithExclusions(T obj, String[] exclusions) {
        return serialization(obj, exclusions, null);
    }

    /**
     * 反序列化（字节数组 -> 对象），包含所有需要反序列化的属性字段.
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        return deserialization(data, cls, null, null);
    }

    /**
     * 反序列化（字节数组 -> 对象），包含指定的需要反序列化的属性字段.
     */
    public static <T> T deserialize(byte[] data, Class<T> cls, String[] fields) {
        return deserialization(data, cls, null, fields);
    }

    /**
     * 反序列化（字节数组 -> 对象），排除对应的属性字段.
     */
    public static <T> T deserializeWithExclusions(byte[] data, Class<T> cls, String[] fields) {
        return deserialization(data, cls, fields, null);
    }

    private static <T> byte[] serialization(T obj, String[] exclusions, String[] inclusives) {
        if (null == obj) return null;
        @SuppressWarnings("unchecked")
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = initSchema(exclusions, inclusives, cls);
            return ProtobufIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
        return null;
    }

    private static <T> Schema<T> initSchema(String[] exclusions, String[] inclusives, Class<T> cls) {
        if (null != exclusions && exclusions.length != 0) {
            return getSchemaWithExclusion(cls, exclusions);
        } else if (null != inclusives && inclusives.length != 0) {
            return getSchemaWithInclusive(cls, inclusives);
        } else {
            return getSchema(cls);
        }
    }

    /**
     * 反序列化（字节数组 -> 对象），包含指定的需要反序列化的属性字段.
     */
    private static <T> T deserialization(byte[] data, Class<T> cls, String[] exclusions, String[] inclusives) {
        if (data == null || data.length == 0)
            return null;
        try {
            T message = objenesis.newInstance(cls);
            Schema<T> schema = initSchema(exclusions, inclusives, cls);
            ProtobufIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private static String getKey(@SuppressWarnings("rawtypes") Class cls) {
        List<String> sorts = getSortedFields(cls);
        return cls.getName() + sorts.toString();
    }

    private static String getKeyWithExclusion(@SuppressWarnings("rawtypes") Class cls, String[] fields) {
        List<String> sorts = getSortedFields(cls);
        List<String> exclusions = Arrays.asList(fields);
        sorts.removeAll(exclusions);
        return cls.getName() + sorts.toString();
    }

    private static String getKeyWithInclusion(@SuppressWarnings("rawtypes") Class cls, String[] fields) {
        List<String> l = Arrays.asList(fields);
        //以下排序逻辑的初忠是为了对相同字段列表做去重操作的，而实际运行中，字段列表相同而序列顺序会有不同的情况，所以需要注释排序。
        /*List<String> sorts = new ArrayList<String>(l);
        Collections.sort(sorts);*/
        return cls.getName() + l.toString();
    }

    private static List<String> getSortedFields(@SuppressWarnings("rawtypes") Class cls) {
        Field[] fields = cls.getDeclaredFields();
        String[] strs = new String[fields.length];

        int i = 0;
        for (Field field : fields) {
            strs[i] = field.getName();
            i++;
        }
        Arrays.sort(strs);
        return new ArrayList<>(Arrays.asList(strs));
    }

    private static <T> Schema<T> getSchema(Class<T> cls) {
        String key = getKey(cls);
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) cachedSchema.get(key);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            cachedSchema.put(key, schema);
        }
        return schema;
    }

    private static <T> Schema<T> getSchemaWithExclusion(Class<T> cls, String[] fields) {
        String key = getKeyWithExclusion(cls, fields);

        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) cachedSchema.get(key);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls, fields, RuntimeEnv.ID_STRATEGY);
            cachedSchema.put(key, schema);
        }
        return schema;
    }

    private static <T> Schema<T> getSchemaWithInclusive(Class<T> cls, String[] fields) {
        String key = getKeyWithInclusion(cls, fields);

        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) cachedSchema.get(key);
        if (schema == null) {
            Map<String, String> map = new LinkedHashMap<>();
            for (String field : fields) {
                map.put(field, field);
            }
            schema = RuntimeSchema.createFrom(cls, map, RuntimeEnv.ID_STRATEGY);
            cachedSchema.put(key, schema);
        }
        return schema;
    }
}

