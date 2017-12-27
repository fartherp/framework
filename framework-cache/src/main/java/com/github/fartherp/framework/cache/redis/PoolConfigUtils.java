/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/12/27
 */
public class PoolConfigUtils {
    /**
     * operation time out
     */
    public static int timeout = Protocol.DEFAULT_TIMEOUT;

    /**
     * if maxIdle == 0, ObjectPool has 0 size pool
     */
    public static int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;

    /**
     * max wait time
     */
    public static long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;

    /**
     * set if support test client workable on get client
     */
    public static boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;

    /**
     * set min idle count
     */
    public static int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;

    /**
     * set max idle count
     */
    public static int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;

    /**
     * set if support test client workable on return client to pool
     */
    public static boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;

    /**
     * set if support test client workable while idle
     */
    public static boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;

    /**
     * set time between eviction runs during in Milliseconds
     */
    public static long timeBetweenEvictionRunsMillis = GenericObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    /**
     * set number tests per eviction to run
     */
    public static int numTestsPerEvictionRun = GenericObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

    /**
     * set min evictable idle count in milliseconds
     */
    public static long minEvictableIdleTimeMillis = GenericObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    /**
     * softMinEvictableIdleTimeMillis
     */
    public static long softMinEvictableIdleTimeMillis = GenericObjectPool.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    /**
     * lifo
     */
    public static boolean lifo = GenericObjectPool.DEFAULT_LIFO;

    public static GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // maxIdle为负数时，sop中不对pool size大小做限制，此处做限制，防止保持过多空闲redis连接
            if (maxIdle >= 0) {
            poolConfig.setMaxIdle(maxIdle);
        }
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setTestOnReturn(testOnReturn);
        poolConfig.setTestWhileIdle(testWhileIdle);
        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        poolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        poolConfig.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
        poolConfig.setLifo(lifo);
        return poolConfig;
    }
}
