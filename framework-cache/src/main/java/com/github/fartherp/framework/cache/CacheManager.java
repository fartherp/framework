/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/12/1
 */
public interface CacheManager {
    /**
     * 往Cache里存数据
     *
     * @param key
     *            关键字
     * @param obj
     *            数据对象
     * @return 返回保存成功的domainName，如果保存不成功则返回null
     */
    String put(Object key, Object obj);

    /**
     * 往Cache里存数据
     *
     * @param key
     *            关键字
     * @param expiration
     *            过期时间 seconds
     * @param obj
     *            数据对象
     * @return domain name
     */
    String put(Object key, Integer expiration, Object obj);

    /**
     * 从Cache取数据
     *
     * @param key
     *            关键字
     * @return 数据对象
     */
    Object get(Object key);

    /**
     * 从Cache里清除数据
     *
     * @param key
     *            关键字
     * @return 返回清除成功的domainName
     */
    String remove(Object key);

    /**
     * 往Cache里置换数据
     *
     * @param key
     *            关键字
     * @param obj
     *            数据对象
     * @return 返回置换成功的domainName，如果置换不成功则返回null
     */
    String replace(Object key, Object obj);

    /**
     * 往Cache里置换数据
     *
     * @param key
     *            关键字
     * @param expiration
     *            过期时间
     * @param obj
     *            数据对象
     * @return 返回置换成功的domainName，如果置换不成功则返回null
     */
    String replace(Object key, Integer expiration, Object obj);

    /**
     * 检查是否存在关键字
     * @param key
     *            关键字
     * @return true：存在，false：不存在
     */
    boolean existsKey(String key);

    /**
     * 延长过期时间
     *
     * @param key 关键字
     * @param expirationMs
     *            time in seconds
     * @return true：成功，false：失败
     */
    boolean extendTime(String key, Integer expirationMs);

    /**
     * 停止所有客户端缓存
     */
    void shutdown();
}
