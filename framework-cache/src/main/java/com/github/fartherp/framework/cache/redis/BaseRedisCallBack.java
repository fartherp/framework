/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/12/1
 */
public abstract class BaseRedisCallBack<T> implements RedisCallBack<T> {
    /**
     * 日志类
     */
    private final Log LOGGER = LogFactory.getLog(getClass());
    /**
     * 当前执行失败的异常
     */
    private Exception e;
    /**
     * redis服务器返回的实际结果
     */
    private T result;
    /**
     * if support get null value do retry get
     */
    private boolean nullValueReGet;

    public final boolean operation(List<RedisClient> clients, boolean read, Object key,
                                   RedisClientStatusNotifier notifier) {
        boolean success = false;
        for (RedisClient client : clients) {
            long time1 = System.currentTimeMillis();
            try {
                result = doOperation(client);
                long time2 = System.currentTimeMillis();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("[cacheTask:" + getOptionType() + "]"
                            + " <key:" + key + "> <redis client : "
                            + client.getCacheName() + "> <server："
                            + client.getRedisServer() + "> success ! (use ："
                            + (time2 - time1) + " ms)");
                }
                if (read) {
                    if (nullValueReGet && result == null) {
                        // 重试另一个客户端
                        continue;
                    }
                    // 成功直接返回
                    return true;
                }
                success = success || true;
            } catch (Exception e) {
                if (notifier != null) {
                    notifier.onFaild(client);
                }
                success = success || false;
                this.e = e;
                long time2 = System.currentTimeMillis();
                LOGGER.error("[[cacheTask:" + getOptionType() + "]" + " <key:"
                        + key + "> <redis client : " + client.getCacheName()
                        + "> <server：" + client.getRedisServer()
                        + "> fail ! (use ：" + (time2 - time1) + " ms)");
            }
        }

        return success;
    }

    /**
     * 实际执行的命令
     *
     * @param client {@link RedisClient} instance
     * @return 操作返回结果
     * @throws Exception in case of process cache exception
     */
    protected abstract T doOperation(RedisClient client) throws Exception;

    public T getResult() {
        return result;
    }

    public Exception getException() {
        return e;
    }

    public boolean isNullValueReGet() {
        return nullValueReGet;
    }

    public void setNullValueReGet(boolean nullValueReGet) {
        this.nullValueReGet = nullValueReGet;
    }
}
