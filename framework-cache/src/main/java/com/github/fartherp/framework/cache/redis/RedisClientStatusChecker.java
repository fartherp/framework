/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/12/1
 */
public interface RedisClientStatusChecker {
    /**
     * 检查客户端状态
     *
     * @param redisClient redis客户端实例
     * @return true：客户端正常false：客户端异常
     */
    boolean checkStatus(RedisClient redisClient);
}
