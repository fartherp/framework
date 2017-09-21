/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * a implement of CacheManager in Redis
 * <p>
 * a example:
 *
 * <pre>
 * {@code
 * <bean id="myCacheManager" class="cn.vansky.framework.core.cache.redis.HaRedisCacheManager">
 *     <property name="clientList">
 *         <list>
 *             <ref local="client"/>
 *         </list>
 *     </property>
 *     <property name="retryTimes" value="3" />
 * </bean>
 *
 * <bean id="client" class="cn.vansky.framework.core.cache.redis.RedisClient">
 *     <property name="redisServer">
 *         <value>127.0.0.1</value>
 *     </property>
 *     <property name="port">
 *         <value>6379</value>
 *     </property>
 *     <property name="redisAuthKey">
 *         <value>123456</value>
 *     </property>
 * </bean>
 * }
 * </pre>
 * Author: CK
 * Date: 2015/12/1
 */
public class HaRedisCacheManager extends AbstractRedisCacheManager implements DisposableBean {
    /**
     * 日志类
     */
    private final Log LOGGER = LogFactory.getLog(getClass());
    /**
     * 心跳线程池
     */
    private ExecutorService es;
    /**
     * 失败的redis客户端
     */
    private Set<RedisClient> failedClients;
    /**
     * validate interval in milliseconds
     */
    private long validInterval = 1000L;
    /**
     * 心跳对象
     */
    private RedisClientHeartBeat rchb;
    /**
     * 心跳是否可用
     */
    private boolean enableHeartBeat = true;
    /**
     * redis客户端状态检查类
     */
    private RedisClientStatusChecker redisClientStatusChecker;

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        if (failedClients == null) {
            failedClients = new CopyOnWriteArraySet<RedisClient>();
        }

        if (!enableHeartBeat) {
            LOGGER.warn("HA redis manager is disabled heart beat. if you want to"
                    + " enable it to set enableHeartBeat=true");
        } else {
            if (redisClientStatusChecker == null) {
                redisClientStatusChecker = new RedisClientStatusPingChecker();
                LOGGER.warn("property 'redisClientStatusChecker' is null use RedisClientStatusPingChecker");
            }
        }
    }

    public void onFaild(RedisClient client) {
        // add to failed status
        failedClients.add(client);
        // remove failed client
        getClientList().remove(client);
        executeHeartBeat();
    }

    public void onOk(RedisClient client) {
        // recover to client list
        if (!getClientList().contains(client)) {
            getClientList().add(client);
        }
        // remove to failed client
        failedClients.remove(client);
    }

    public void destroy() throws Exception {
        if (rchb != null) {
            // 关闭心跳类
            rchb.close();
        }
        if (es != null) {
            // 关闭线程池
            es.shutdown();
        }
    }

    /**
     * 执行心跳
     */
    private synchronized void executeHeartBeat() {
        if (!enableHeartBeat) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("HA redis manager is disabled heart beat.");
            }
            return;
        }

        if (redisClientStatusChecker == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("redisClientStatusChecker is null so should disable heart beat.");
            }
            return;
        }

        if (rchb == null) {
            rchb = new RedisClientHeartBeat(this);
            if (es == null) {
                es = Executors.newFixedThreadPool(1);
            }
            es.execute(rchb);
        } else {
            if (!rchb.isRuning()) {
                if (es == null) {
                    es = Executors.newFixedThreadPool(1);
                }
                es.execute(rchb);
            }
        }
    }

    public String toString() {
        int size = 0;
        if (CollectionUtils.isNotEmpty(getClientList())) {
            size = getClientList().size();
        }
        return "HaRedisCacheManager clientSize[" + size + "]";
    }

    protected List<RedisClient> getClients(Object key) {
        return new ArrayList<RedisClient>(getClientList());
    }

    protected Set<RedisClient> getFailedClients() {
        return failedClients;
    }

    public long getValidInterval() {
        return validInterval;
    }

    public void setValidInterval(long validInterval) {
        this.validInterval = validInterval;
    }

    public void setRedisClientStatusChecker(RedisClientStatusChecker redisClientStatusChecker) {
        this.redisClientStatusChecker = redisClientStatusChecker;
    }

    public void setEnableHeartBeat(boolean enableHeartBeat) {
        this.enableHeartBeat = enableHeartBeat;
    }

    private static class RedisClientHeartBeat implements Runnable {
        private final Log LOGGER = LogFactory.getLog(getClass());
        private boolean runing;
        private HaRedisCacheManager redisCacheManager;

        public RedisClientHeartBeat(HaRedisCacheManager redisCacheManager) {
            super();
            this.redisCacheManager = redisCacheManager;
        }

        private boolean close = false;

        public synchronized boolean isRuning() {
            return runing;
        }

        public synchronized void close() {
            close = true;
        }

        public void run() {
            runing = true;

            while (!redisCacheManager.getFailedClients().isEmpty() && !close) {
                if (redisCacheManager.redisClientStatusChecker == null) {
                    break;
                }

                List<RedisClient> clients = new ArrayList<RedisClient>(redisCacheManager.getFailedClients());
                for (RedisClient redisClient : clients) {
                    if (redisCacheManager.redisClientStatusChecker.checkStatus(redisClient)) {
                        redisCacheManager.onOk(redisClient);
                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("Redis Client[" + redisClient.getRedisServer()
                                    + "] heartbeat recover success!");
                        }
                    }
                }
                try {
                    // 心跳休眠
                    Thread.sleep(redisCacheManager.getValidInterval());
                } catch (Exception e) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(e.getMessage(), e);
                    }
                }
            }
            runing = false;
        }
    }
}
