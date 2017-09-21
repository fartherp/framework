/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/12/1
 */
public interface ExtCacheManager extends CacheManager {
    /**
     * 向名称为key的hash中添加元素field
     *
     * @param key
     *            关键字
     * @param field
     *            关键字对应的字段
     * @param fieldValue
     *            关键字对应的字段的值
     */
    void hput(String key, String field, Serializable fieldValue);

    /**
     * 返回名称为key的hash中field对应的value
     *
     * @param key
     *            关键字
     * @param field
     *            关键字对应的字段
     * @return 关键字对应的字段的值
     */
    Object hget(String key, String field);

    /**
     * 删除名称为key的hash中键为field的域
     *
     * @param key
     *            散列关键字
     * @param field
     *            关键字对应的字段
     * @return true：成功，false：失败
     */
    boolean hdel(String key, String field);

    /**
     * 返回名称为key的hash中所有键
     *
     * @param key
     *            关键字
     * @return 所有字段
     */
    Set<String> hKeys(String key);

    /**
     * 返回名称为key的hash中所有键对应的value
     *
     * @param key
     *            关键字
     * @return 所有字段的值
     */
    List<Object> hValues(String key);

    /**
     * 名称为key的hash中是否存在键为field的值
     *
     * @param key
     *            关键字
     * @param field
     *            关键字对应的字段
     * @return true：存在，false：不存在
     */
    boolean hExists(String key, String field);

    /**
     * 返回名称为key的hash中元素个数
     *
     * @param key
     *            关键字
     * @return 元素个数
     */
    long hLen(String key);

    /**
     * 向名称为key的hash中添加元素field
     *
     * @param key
     *            关键字
     * @param values
     *            <field，value>
     */
    void hmSet(String key, Map<String, Serializable> values);

    /**
     * 返回名称为key的hash中fields对应的值list
     *
     * @param key
     *            关键字
     * @param fields
     *            对应的字段
     * @return 字段对应的值列表
     */
    List<Object> hmGet(String key, String... fields);

    /**
     * 返回名称为key的hash中所有的键（field）及其对应的value
     *
     * @param key
     *            关键字
     * @return <field，value>
     */
    Map<String, Object> hGetAll(String key);
}
