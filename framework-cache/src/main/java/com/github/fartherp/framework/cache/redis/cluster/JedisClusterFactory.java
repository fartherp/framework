/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis.cluster;

import com.github.fartherp.framework.cache.redis.PoolConfigUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * redis集群
 * <p>
 * a example:
 *
 * <pre>
 * {@code
 * <bean id="jedisCluster" class="com.github.fartherp.framework.cache.redis.cluster.JedisClusterFactory">
 *     <property name="address" value="192.168.1.1:6789;192.168.1.2:6789;192.168.1.3:6789;" />
 *     <property name="timeout" value="300000" />
 *     <property name="maxAttempts" value="6" />
 * </bean>
 *
 * }
 * </pre>
 * @author: CK
 * @date: 2017/12/27
 */
public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {
    private static final Log logger = LogFactory.getLog(JedisClusterFactory.class);
    private String address;
    private JedisCluster jedisCluster;
    private int timeout = PoolConfigUtils.timeout;
    private int maxAttempts = PoolConfigUtils.maxActive;
    private GenericObjectPoolConfig genericObjectPoolConfig;

    public JedisCluster getObject() throws Exception {
        return this.jedisCluster;
    }

    public Class<?> getObjectType() {
        return JedisCluster.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        if (this.genericObjectPoolConfig == null) {
            this.genericObjectPoolConfig = PoolConfigUtils.genericObjectPoolConfig();
        }
        if (this.jedisCluster == null) {
            Set<HostAndPort> jedisClusterNode = getHostAndPorts();
            this.jedisCluster = new JedisCluster(jedisClusterNode, timeout, maxAttempts, genericObjectPoolConfig);
        }
        logger.info("redis 集群信息: " + this.jedisCluster.getClusterNodes());
    }

    public Set<HostAndPort> getHostAndPorts() {
        String[] addresss = this.address.split(";");
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();

        if (addresss == null || addresss.length == 0) {
            throw new RuntimeException("没有配置redis地址");
        }

        for (String add : addresss) {
            String[] ipAndPort = add.split(":");
            if (ipAndPort != null && ipAndPort.length != 2) {
                throw new RuntimeException("redis格式错误, 格式 ip1:port1;ip2:port2");
            }
            HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
            jedisClusterNode.add(hap);
        }

        return jedisClusterNode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public GenericObjectPoolConfig getGenericObjectPoolConfig() {
        return genericObjectPoolConfig;
    }

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
        this.genericObjectPoolConfig = genericObjectPoolConfig;
    }
}
