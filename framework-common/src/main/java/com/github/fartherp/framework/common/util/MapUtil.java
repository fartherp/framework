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
 * @author CK
 * @date 2015/11/13
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
            if (map == null || map.isEmpty()) {
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
        List<String> ignoreList = (ignoreProperties != null && ignoreProperties.length > 0)
			? Arrays.asList(ignoreProperties) : null;
        Map<String, Object> m = new HashMap<>(ts.length);
        for (PropertyDescriptor t : ts) {
            Method r = t.getReadMethod();
            String name = t.getName();
            if (r != null && (ignoreList == null || !ignoreList.contains(name))) {
                try {
                    if (!Modifier.isPublic(r.getDeclaringClass().getModifiers())) {
                        r.setAccessible(true);
                    }
                    String value;
                    if (null != map && (value = map.get(name)) != null) {
                        m.put(value, r.invoke(o));
                    } else {
                        m.put(name, r.invoke(o));
                    }
                } catch (Throwable e) {
                    throw new RuntimeException("Could not copy property '" + name + "' from source to target", e);
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
        List<String> ignoreList = (ignoreProperties != null && ignoreProperties.length > 0)
			? Arrays.asList(ignoreProperties) : null;
        for (PropertyDescriptor t : ts) {
            String name = t.getName();
            if (ignoreList == null || !ignoreList.contains(name)) {
            	Object value;
                if (null != source && (value = source.get(name)) != null) {
                    ReflectUtil.setValueByField(target, name, value);
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

		map.forEach((k, v) -> {
			Map<String, Object> result = new HashMap<>();
			result.put("text", v.toString());
			result.put("value", k);
			if (null != defaultValue && k.equals(defaultValue.toString())) {
				result.put("selected", true);
			}
			list.add(result);
		});

        return list;
    }
}
