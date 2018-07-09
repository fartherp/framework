/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis.cluster;

import com.github.fartherp.framework.common.util.DateUtil;
import com.github.fartherp.framework.common.util.JsonUtil;
import org.testng.annotations.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/12/27
 */
public class JedisClusterFactoryTest {
//    @Test
    public void testGetHostAndPorts() throws Exception {
        JedisClusterFactory jedisClusterFactory = new JedisClusterFactory();
        jedisClusterFactory.setAddress("192.168.1.1:6789;192.168.1.2:6789;192.168.1.3:6789");
        Set<HostAndPort> hostAndPorts = jedisClusterFactory.getHostAndPorts();
        assertEquals(hostAndPorts.size(), 3);
    }

    public static void main(String[] args) throws Exception {
        JedisClusterFactory jedisClusterFactory = new JedisClusterFactory();
        jedisClusterFactory.setAddress("192.168.9.85:20006;192.168.9.85:20007;192.168.9.85:20008;192.168.9.85:20009;192.168.9.85:20010;192.168.9.85:20011");
        Set<HostAndPort> hostAndPorts = jedisClusterFactory.getHostAndPorts();
        jedisClusterFactory.afterPropertiesSet();

        A a = new A();
        a.setBigDecimal(new BigDecimal("64561.6466632131312312313123131"));
        String s = JsonUtil.toJson(a);

        jedisClusterFactory.getJedisCluster().set("cyq.test.key", s, "NX", "EX", 60);
//        int count = 2;
//        while (count > 0) {
//            LockThread thread = new LockThread();
//            thread.count = count;
//            thread.jedisClusterFactory = jedisClusterFactory;
//            thread.start();
//            count--;
//        }
    }

    public static class A {
        private BigDecimal bigDecimal;

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }
    }

    public static class LockThread extends Thread {
        public int count;
        public JedisClusterFactory jedisClusterFactory;

        @Override
        public void run() {
            String lockKey = "cyq-lock-key";
            String requestId = UUID.randomUUID().toString();
            long expireTime = 10;
            boolean flag = false;
            while (!flag) {
                flag = tryGetDistributedLock(jedisClusterFactory.getJedisCluster(), lockKey, requestId, expireTime, count);
                if (flag) {
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            releaseDistributedLock(jedisClusterFactory.getJedisCluster(), lockKey, requestId, count);
        }
    }

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(JedisCluster jedis, String lockKey, String requestId, long expireTime, int count) {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        System.out.println(DateUtil.format(DateUtil.yyyy_MM_dd_HH_mm_ss, new Date()) + "|tryGetDistributedLock----------------" + count + "|" + result);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(JedisCluster jedis, String lockKey, String requestId, int count) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        System.out.println(DateUtil.format(DateUtil.yyyy_MM_dd_HH_mm_ss, new Date()) + "|releaseDistributedLock----------------" + count + "|" + result);
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}