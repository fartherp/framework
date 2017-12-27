/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis.cluster;

import org.testng.annotations.Test;
import redis.clients.jedis.HostAndPort;

import java.util.Set;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/12/27
 */
public class JedisClusterFactoryTest {
    @Test
    public void testGetHostAndPorts() throws Exception {
        JedisClusterFactory jedisClusterFactory = new JedisClusterFactory();
        jedisClusterFactory.setAddress("192.168.1.1:6789;192.168.1.2:6789;192.168.1.3:6789");
        Set<HostAndPort> hostAndPorts = jedisClusterFactory.getHostAndPorts();
        assertEquals(hostAndPorts.size(), 3);
    }

}