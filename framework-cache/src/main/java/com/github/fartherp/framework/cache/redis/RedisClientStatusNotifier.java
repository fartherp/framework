/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/12/1
 */
public interface RedisClientStatusNotifier {
    /**
     * 客户端失败处理
     *
     * @param client redis客户端
     */
    void onFaild(RedisClient client);

    /**
     * 客户端成功处理
     *
     * @param client redis客户端
     */
    void onOk(RedisClient client);
}
