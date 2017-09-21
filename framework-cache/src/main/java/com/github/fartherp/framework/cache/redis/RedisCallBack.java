/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/12/1
 */
public interface RedisCallBack<T> {
    /**
     * do real operation to redis server
     *
     * @param client
     *            {@link RedisClient} list
     * @param read
     *            if read action
     * @param key
     *            关键字
     * @param notifier
     *            {@link RedisClientStatusNotifier} instance
     * @return true：成功，false：失败
     */
    boolean operation(List<RedisClient> client, boolean read, Object key, RedisClientStatusNotifier notifier);

    /**
     * 获取操作类型
     *
     * @return 操作类型
     */
    String getOptionType();

    /**
     * 获取操作结果
     *
     * @return 操作结果
     */
    T getResult();

    /**
     * 获取操作异常，成功为null
     *
     * @return 操作异常
     */
    Exception getException();

    /**
     * get if support retry get on return null value
     *
     * @return true support retry get on return null value
     */
    boolean isNullValueReGet();

    /**
     * set if support retry get on return null value
     *
     * @param nullValueReGet
     *            true support retry get on return null value
     */
    void setNullValueReGet(boolean nullValueReGet);
}
