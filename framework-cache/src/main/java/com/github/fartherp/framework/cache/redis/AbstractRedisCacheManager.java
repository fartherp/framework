/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis;

import com.github.fartherp.framework.cache.ExtCacheManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/12/1
 */
public abstract class AbstractRedisCacheManager implements ExtCacheManager, RedisClientStatusNotifier,
        InitializingBean {
    /**
     * list of {@link RedisClient}
     */
    private List<RedisClient> clientList;

    /**
     * log this class
     */
    private final Log LOGGER = LogFactory.getLog(getClass());

    /**
     * default retry times
     */
    private static final int DEFALUT_RETRY_TIMES = 3;
    /**
     * retry times
     */
    private int retryTimes = DEFALUT_RETRY_TIMES;

    /**
     * set true will retry another server on get a <code>null</code> value
     */
    private boolean nullValueReGet = false;

    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(getClientList())) {
            throw new IllegalArgumentException("client list is empty!");
        }
    }

    public void shutdown() {
        if (clientList == null) {
            return;
        }
        for (RedisClient redisClient : clientList) {
            try {
                redisClient.shutdown();
            } catch (Exception e) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
    }

    /**
     * check {@link RedisClient} list is not empty throws RuntimeException in
     * case of empty client list
     *
     * @param clients {@link RedisClient} list
     * @return true if {@link RedisClient} list is not empty
     */
    private boolean checkClients(List<RedisClient> clients) {
        if (CollectionUtils.isEmpty(clients)) {
            throw new RuntimeException("No redis Client available. maybe all clients failed to connect server.");
        }
        return true;
    }

    public String put(final Object key, final Object obj) {
        return put(key, -1, obj);
    }

    public String put(final Object key, final Integer expiration, final Object obj) {
        List<RedisClient> clients = this.getClients(key);
        String cacheName = null;
        if (checkClients(clients)) {
            cacheName = clients.get(0).getCacheName();
            this.exeCommand(new BaseRedisCallBack<Boolean>() {
                public Boolean doOperation(RedisClient redisClient) throws Exception {
                    return redisClient.set(key.toString(), obj, expiration);
                }
                public String getOptionType() {
                    return "PUT";
                }
            }, key, false);
        }
        return cacheName;
    }

    public String replace(final Object key, final Object obj) {
        return replace(key, -1, obj);
    }

    public String replace(final Object key, final Integer expiration, final Object obj) {
        return put(key, expiration, obj);
    }

    public Object get(final Object key) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<Object>() {
                public Object doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.get(key.toString());
                }
                public String getOptionType() {
                    return "GET";
                }
            }, key, true);
        }
        return null;
    }

    public String remove(final Object key) {
        List<RedisClient> clients = this.getClients(key);
        String cacheName = null;
        if (checkClients(clients)) {
            cacheName = clients.get(0).getCacheName();
            this.exeCommand(new BaseRedisCallBack<Boolean>() {
                public Boolean doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.delete(key.toString());
                }
                public String getOptionType() {
                    return "REMOVE";
                }
            }, key, false);
        }
        return cacheName;
    }

    public boolean existsKey(final String key) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<Boolean>() {
                public Boolean doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.exists(key);
                }
                public String getOptionType() {
                    return "EXIST";
                }
            }, key, true);
        }
        return false;
    }

    public boolean extendTime(final String key, final Integer expirationMs) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<Boolean>() {
                public Boolean doOperation(RedisClient redisClient) throws Exception {
                    return redisClient.expire(key, expirationMs / 1000);
                }
                public String getOptionType() {
                    return "EXPIRE";
                }
            }, key, false);
        }
        return false;
    }

    public void hput(final String key, final String field, final Serializable fieldValue) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            this.exeCommand(new BaseRedisCallBack<Object>() {
                public Object doOperation(RedisClient redisClient) throws Exception {
                    redisClient.hput(key, field, fieldValue);
                    return null;
                }
                public String getOptionType() {
                    return "HPUT";
                }
            }, key, false);
        }
    }

    public Object hget(final String key, final String field) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<Object>() {
                public Object doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.hget(key, field);
                }
                public String getOptionType() {
                    return "HGET";
                }
            }, key, true);
        }
        return null;
    }

    public boolean hdel(final String key, final String field) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<Boolean>() {
                public Boolean doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.hdel(key, field);
                }

                public String getOptionType() {
                    return "HDEL";
                }
            }, key, false);
        }
        return false;
    }

    public Set<String> hKeys(final String key) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<Set<String>>() {
                public Set<String> doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.hKeys(key);
                }

                public String getOptionType() {
                    return "HKEYS";
                }
            }, key, true);
        }
        return Collections.emptySet();
    }

    public List<Object> hValues(final String key) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<List<Object>>() {
                public List<Object> doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.hValues(key);
                }
                public String getOptionType() {
                    return "HVALUES";
                }
            }, key, true);
        }
        return Collections.emptyList();
    }

    public boolean hExists(final String key, final String field) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<Boolean>() {
                public Boolean doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.hExists(key, field);
                }
                public String getOptionType() {
                    return "HEXISTS";
                }
            }, key, true);
        }
        return false;
    }

    public long hLen(final String key) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<Long>() {
                public Long doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.hLen(key);
                }
                public String getOptionType() {
                    return "HLEN";
                }
            }, key, true);
        }
        return 0;
    }

    public void hmSet(final String key, final Map<String, Serializable> values) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            this.exeCommand(new BaseRedisCallBack<Object>() {
                public Object doOperation(RedisClient redisClient) throws Exception {
                    redisClient.hmSet(key, values);
                    return null;
                }
                public String getOptionType() {
                    return "HMSET";
                }
            }, key, false);
        }
    }

    public List<Object> hmGet(final String key, final String... fields) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(new BaseRedisCallBack<List<Object>>() {
                public List<Object> doOperation(RedisClient redisClient)
                        throws Exception {
                    return redisClient.hmGet(key, fields);
                }
                public String getOptionType() {
                    return "HMGET";
                }
            }, key, true);
        }
        return Collections.emptyList();
    }

    public Map<String, Object> hGetAll(final String key) {
        List<RedisClient> clients = this.getClients(key);
        if (checkClients(clients)) {
            return this.exeCommand(
                    new BaseRedisCallBack<Map<String, Object>>() {
                        public Map<String, Object> doOperation(RedisClient redisClient) throws Exception {
                            return redisClient.hGetAll(key);
                        }
                        public String getOptionType() {
                            return "HGETALL";
                        }
                    }, key, true);
        }
        return Collections.emptyMap();
    }

    /**
     * 执行redis命令
     *
     * @param <T>           target cache type
     * @param redisCallBack 回调对象
     * @param key           关键字
     * @param read          is read action
     * @return 返回结果
     */
    private <T> T exeCommand(RedisCallBack<T> redisCallBack, Object key, boolean read) {
        redisCallBack.setNullValueReGet(isNullValueReGet());
        // 重试机制
        for (int i = 0; i < getRetryTimes(); i++) {
            boolean result = redisCallBack.operation(getClients(key), read, key, this);
            if (result) {
                return redisCallBack.getResult();
            }
        }

        // 有异常直接抛出
        if (redisCallBack.getException() != null) {
            throw new RuntimeException(redisCallBack.getException().getMessage(), redisCallBack.getException());
        }
        return null;
    }

    protected abstract List<RedisClient> getClients(Object key);

    public void onFaild(RedisClient client) {
    }

    public void onOk(RedisClient client) {
    }

    protected final boolean isNullValueReGet() {
        return nullValueReGet;
    }

    public void setNullValueReGet(boolean nullValueReGet) {
        this.nullValueReGet = nullValueReGet;
    }

    protected int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        if (retryTimes < 1) {
            this.retryTimes = DEFALUT_RETRY_TIMES;
        } else {
            this.retryTimes = retryTimes;
        }
        LOGGER.warn("set RedisCacheManager retry times to " + this.retryTimes);
    }

    protected List<RedisClient> getClientList() {
        return clientList;
    }

    public void setClientList(List<RedisClient> clientList) {
        this.clientList = new ArrayList<RedisClient>(clientList);
    }
}
