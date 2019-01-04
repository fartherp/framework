/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装Map操作工具类
 * Author: CK
 * Date: 2015/11/13
 */
public class MapUtil {
    /**
     * 辅助容器类
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    public static class MapPlain<K, V> {

        /**
         * 最终生成的map实体.
         */
        Map<K, V> map;

        public MapPlain() {
            this(0);
        }

        public MapPlain(int cap) {
            map = new HashMap<>(cap);
        }

        /**
         * 链式put方法
         *
         * @param key the key
         * @param value the value
         * @return 辅助处理对象
         */
        public MapPlain<K, V> put(K key, V value) {
            this.map.put(key, value);
            return this;
        }

        /**
         * 链式putAll方法.
         *
         * @param map map对象,非null
         * @return the map plain
         */
        public MapPlain<K, V> putAll(Map<K, V> map) {
            if (map == null) {
                return this;
            }
            this.map.putAll(map);
            return this;
        }

        /**
         * 获得最终的Map
         *
         * @return map对象
         */
        public Map<K, V> get() {
            return map;
        }
    }

    /**
     * 构建辅助处理类
     *
     * @param <K> the key type
     * @param <V> the value type
     * @return 新的辅助处理类
     */
    public static <K, V> MapPlain<K, V> build() {
        return new MapPlain<>();
    }

    /**
     * 构建辅助处理类
     *
     * @param <K> the key type
     * @param <V> the value type
     * @return 新的辅助处理类
     */
    public static <K, V> MapPlain<K, V> build(int cap) {
        return new MapPlain<>(cap);
    }

    /**
     * 获取对象属性对应的map
     * @param o 对象
     * @param map 别名扩展
     * @param ignoreProperties 不需要的属性
     * @return map
     */
    public static Map<String, Object> toMap(Object o, Map<String, String> map, String... ignoreProperties) {
        PropertyDescriptor[] ts = ReflectUtil.getPropertyDescriptors(o);
        List<String> ignoreList = (ignoreProperties != null && ignoreProperties.length > 0) ? Arrays.asList(ignoreProperties) : null;
        Map<String,Object> m = new HashMap<>(ts.length);
        for (PropertyDescriptor t : ts) {
            Method r = t.getReadMethod();
            if (r != null && (ignoreList == null || (!ignoreList.contains(t.getName())))) {
                try {
                    if (!Modifier.isPublic(r.getDeclaringClass().getModifiers())) {
                        r.setAccessible(true);
                    }
                    if (null != map && map.containsKey(t.getName())) {
                        m.put(map.get(t.getName()), r.invoke(o));
                    } else {
                        m.put(t.getName(), r.invoke(o));
                    }
                }
                catch (Throwable e) {
                    throw new RuntimeException("Could not copy property '" + t.getName()
                            + "' from source to target", e);
                }
            }
        }
        return m;
    }

    /**
     * 获取对象属性对应的map
     * @param o 对象
     * @param ignoreProperties 不需要的属性
     * @return map
     */
    public static Map<String, Object> toMap(Object o, String... ignoreProperties) {
        return toMap(o, null, ignoreProperties);
    }

    /**
     * map转成对象
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreProperties 不需要设置值的属性
     */
    public static void toObject(Map<String, Object> source, Object target, String... ignoreProperties) {
        PropertyDescriptor[] ts = ReflectUtil.getPropertyDescriptors(target);
        List<String> ignoreList = (ignoreProperties != null && ignoreProperties.length > 0) ? Arrays.asList(ignoreProperties) : null;
        for (PropertyDescriptor t : ts) {
            String name = t.getName();
            if (ignoreList == null || !ignoreList.contains(name)) {
                if (null != source && source.containsKey(name)) {
                    ReflectUtil.setValueByField(target, t.getName(), source.get(name));
                }
            }
        }
    }

    /**
     * 将一个字典map转化成前端规范中的List<Object>样式
     *
     * @param map 字典map
     * @return list
     */
    public static <K, V> List<Map<String, Object>> mapConvertToList(Map<K, V> map) {
        return mapConvertToList(map, null);
    }

    /**
     * 将一个字典map转化成前端规范中的List<Object>样式
     *
     * @param map 字典map
     * @param defaultValue default value
     * @return list
     */
    public static <K, V> List<Map<String, Object>> mapConvertToList(Map<K, V> map, Object defaultValue) {
        List<Map<String, Object>> list = new ArrayList<>();

        if (null == map || map.isEmpty()) {
            return list;
        }

        for (Map.Entry<K, V> entry : map.entrySet()) {
            Map<String, Object> result = new HashMap<>();
            result.put("text", entry.getValue().toString());
            result.put("value", entry.getKey());
            if (null != defaultValue && entry.getKey().toString().equals(defaultValue.toString())) {
                result.put("selected", true);
            }
            list.add(result);
        }

        return list;
    }
}
