/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis客户端
 * Author: CK
 * Date: 2015/12/1
 */
public class RedisClient implements InitializingBean, DisposableBean {
    /**
     * 日志类
     */
    private final Log LOGGER = LogFactory.getLog(getClass());
    /**
     * redis 操作成功返回的常量
     */
    private static final String OPERATION_SUCCESS = "OK";
    /**
     * 缓存名称
     */
    private String cacheName = "default";
    /**
     * redis server ip
     */
    private String redisServer;
    /**
     * redis server authenticate key
     */
    private String redisAuthKey;
    /**
     * {@link JedisPool} instance
     */
    private JedisPool jedisPool;

    /**
     * redis端口
     */
    private int port = Protocol.DEFAULT_PORT;

    /**
     * If the pool in ObjectPool is exhausted (no available idle instances and
     * no capacity to create new ones), this method will either block
     * (WHEN_EXHAUSTED_BLOCK == 1), throw a NoSuchElementException
     * (WHEN_EXHAUSTED_FAIL == 0), or grow (WHEN_EXHAUSTED_GROW == 2 - ignoring
     * maxActive). The length of time that this method will block when
     * whenExhaustedAction == WHEN_EXHAUSTED_BLOCK is determined by the maxWait
     * property.
     */
    private byte whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
    /**
     * 实际对象池实例
     */
    private GenericObjectPool realPool;

    public void afterPropertiesSet() throws Exception {
        GenericObjectPoolConfig poolConfig = PoolConfigUtils.genericObjectPoolConfig();
        jedisPool = new JedisPool(poolConfig, redisServer, port, PoolConfigUtils.timeout, redisAuthKey);
        realPool = getRealPoolInstance();
    }

    /**
     * 获取实际的对象池实例
     *
     * @return 实际的对象池实例
     */
    protected GenericObjectPool getRealPoolInstance() {
        Field internalPoolField = ReflectionUtils.findField(JedisPool.class, "internalPool", GenericObjectPool.class);
        if (internalPoolField != null) {
            internalPoolField.setAccessible(true);
            return (GenericObjectPool) ReflectionUtils.getField(internalPoolField, jedisPool);
        }
        return null;
    }

    /**
     * get value<br>
     * return null if key did not exist
     *
     * @param key cache key
     * @return cache value
     * @throws Exception in case of access redis server exception
     */
    public Object get(String key) throws Exception {
        byte[] data = null;
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            data = jedis.get(SafeEncoder.encode(key));
        } catch (Exception e) {
            // do jedis.quit() and jedis.disconnect()
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
        return this.deserialize(data);
    }

    /**
     * do ping action
     *
     * @return ping result
     * @throws Exception in case of access redis server exception
     */
    public String ping() throws Exception {
        String res = null;
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            res = jedis.ping();
        } catch (Exception e) {
            // do jedis.quit() and jedis.disconnect()
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
        return res;
    }

    /**
     * value set<br>
     * The string can't be longer than 1073741824 bytes (1 GB).
     *
     * @param key        cache key
     * @param value      value
     * @param expiration expiration time 超时时间
     * @return false if redis did not execute the option
     * @throws Exception in case of access redis server exception
     */
    public boolean set(String key, Object value, Integer expiration) throws Exception {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            if (expiration > 0) {
                result = jedis.setex(SafeEncoder.encode(key), expiration, serialize(value));
            } else {
                result = jedis.set(SafeEncoder.encode(key), serialize(value));
            }
            LOGGER.info("set key:" + key + " value：" + value);
            return OPERATION_SUCCESS.equalsIgnoreCase(result);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * set a value without expiration
     *
     * @param key   cache key
     * @param value cache value
     * @return false if redis did not execute the option
     * @throws Exception in case of access redis server exception
     */
    public boolean set(String key, Object value) throws Exception {
        return this.set(key, value, -1);
    }

    /**
     * add if not exists
     *
     * @param key        cache key
     * @param value      cache value
     * @param expiration expiration time
     * @return false if redis did not execute the option
     * @throws Exception in case of access redis server exception
     */
    public boolean add(String key, Object value, Integer expiration) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            // 操作setnx与expire成功返回1，失败返回0，仅当均返回1时，实际操作成功
            Long result = jedis.setnx(SafeEncoder.encode(key), serialize(value));
            if (expiration > 0) {
                result = result & jedis.expire(key, expiration);
            }
            if (result == 1L) {
                LOGGER.info("add key:" + key + " value：" + value);
            } else {
                LOGGER.info("add key: " + key + " failed, key has already exists! ");
            }
            return result == 1L;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * add if not exists
     *
     * @param key   cache eky
     * @param value cache value
     * @return false if redis did not execute the option
     * @throws Exception in case of access redis server exception
     */
    public boolean add(String key, Object value) throws Exception {
        return this.add(key, value, -1);
    }

    /**
     * Test if the specified key exists.
     *
     * @param key cache key
     * @return true if exist
     * @throws Exception in case of access redis server exception
     */
    public boolean exists(String key) throws Exception {
        boolean isExist = false;
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            isExist = jedis.exists(SafeEncoder.encode(key));
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
        return isExist;
    }

    /**
     * Remove the specified keys.
     *
     * @param key cache key
     * @return false if redis did not execute the option
     */
    public boolean delete(String key) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.del(SafeEncoder.encode(key));
            LOGGER.info("delete key:" + key);
            return true;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
        return false;
    }

    /**
     * to add expire time in seconds
     *
     * @param key     cache key
     * @param seconds expiration in seconds
     * @return false if redis did not execute the option
     */
    public boolean expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.expire(SafeEncoder.encode(key), seconds);
            LOGGER.info("expire key:" + key + " time after " + seconds + " seconds.");
            return true;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
        return false;
    }

    /**
     * Delete all the keys of all the existing databases, not just the currently
     * selected one.
     *
     * @return false if redis did not execute the option
     */
    public boolean flushall() {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            result = jedis.flushAll();
            LOGGER.info("redis client name: " + this.getCacheName() + " flushall.");
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
        return OPERATION_SUCCESS.equalsIgnoreCase(result);
    }

    /**
     * do destroy jedis pool
     *
     * @see #jedisPool
     */
    public void shutdown() {
        try {
            this.jedisPool.destroy();
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    /**
     * Get the bytes representing the given serialized object.
     *
     * @param o target value
     * @return byte array
     */
    protected byte[] serialize(Object o) {
        if (o == null) {
            // throw new NullPointerException("Can't serialize null");
            return new byte[0];
        }
        byte[] rv = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(o);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        }
        return rv;
    }

    /**
     * Get the object represented by the given serialized bytes.
     *
     * @param in byte array
     * @return result after deserialized
     */
    protected Object deserialize(byte[] in) {
        Object rv = null;
        try {
            if (in != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(in);
                ObjectInputStream is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (IOException e) {
            LOGGER.warn("Caught IOException decoding %d bytes of data", e);
        } catch (ClassNotFoundException e) {
            LOGGER.warn("Caught CNFE decoding %d bytes of data", e);
        }
        return rv;
    }

    /**
     * add value to set
     *
     * @param key        cache key of set
     * @param field      element key
     * @param fieldValue value
     * @throws Exception in case of access redis server exception
     */
    public void hput(String key, String field, Serializable fieldValue) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field), serialize(fieldValue));
            LOGGER.info("hset key:" + key + " field:" + field);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * get value by element key from set
     *
     * @param key   cache key of set
     * @param field element key
     * @return value
     */
    public Object hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            byte[] value = jedis.hget(SafeEncoder.encode(key), SafeEncoder.encode(field));
            LOGGER.info("hget key:" + key + " field:" + field);
            return deserialize(value);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
        return null;
    }

    /**
     * delete element from set
     *
     * @param key   cache key of set
     * @param field element key
     * @return true if success
     * @throws Exception in case of access redis server exception
     */
    public boolean hdel(String key, String field) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            long value = jedis.hdel(SafeEncoder.encode(key), SafeEncoder.encode(field));
            LOGGER.info("hget key:" + key + " field:" + field);
            return value == 1;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * get keys from set
     *
     * @param key cache key
     * @return keys from set
     * @throws Exception in case of access redis server exception
     */
    public Set<String> hKeys(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            Set<byte[]> hkeys = jedis.hkeys(SafeEncoder.encode(key));
            LOGGER.info("hkeys key:" + key);
            if (CollectionUtils.isEmpty(hkeys)) {
                return new HashSet<String>(1);
            } else {
                Set<String> keys = new HashSet<String>(hkeys.size());
                for (byte[] bb : hkeys) {
                    keys.add(SafeEncoder.encode(bb));
                }
                return keys;
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * get values from set
     *
     * @param key cache key
     * @return value list
     * @throws Exception in case of access redis server exception
     */
    public List<Object> hValues(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            List<byte[]> hvals = jedis.hvals(SafeEncoder.encode(key));
            LOGGER.info("hvals key:" + key);
            if (CollectionUtils.isEmpty(hvals)) {
                return new ArrayList<Object>(1);
            } else {
                List<Object> ret = new ArrayList<Object>(hvals.size());
                for (byte[] bb : hvals) {
                    ret.add(deserialize(bb));
                }
                return ret;
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * check key exist in set
     *
     * @param key   cache key of set
     * @param field element key
     * @return true if exist
     * @throws Exception in case of access redis server exception
     */
    public boolean hExists(String key, String field) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            boolean ret = jedis.hexists(SafeEncoder.encode(key), SafeEncoder.encode(field));
            LOGGER.info("hexists key:" + key + " field:" + field);
            return ret;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * get size from set
     *
     * @param key cache key
     * @return set size
     * @throws Exception in case of access redis server exception
     */
    public long hLen(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            long ret = jedis.hlen(SafeEncoder.encode(key));
            LOGGER.info("hlen key:" + key);
            return ret;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * decode map value
     *
     * @param values map value
     * @return map value after decoded
     */
    private Map<String, Object> decodeMap(final Map<byte[], byte[]> values) {
        if (MapUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }
        Map<byte[], byte[]> copy = new HashMap<byte[], byte[]>(values);
        Iterator<Map.Entry<byte[], byte[]>> iterator = copy.entrySet().iterator();
        Map<String, Object> ret = new HashMap<String, Object>();
        while (iterator.hasNext()) {
            Map.Entry<byte[], byte[]> next = iterator.next();
            ret.put(SafeEncoder.encode(next.getKey()), deserialize(next.getValue()));
        }

        return ret;
    }

    /**
     * get map value from set
     *
     * @param key cache key
     * @return map value
     * @throws Exception in case of access redis server exception
     */
    public Map<String, Object> hGetAll(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            Map<byte[], byte[]> hgetAll = jedis.hgetAll(SafeEncoder.encode(key));
            LOGGER.info("hgetAll key:" + key);
            return decodeMap(hgetAll);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * batch encode
     *
     * @param values map value to encode
     * @return map result
     */
    private Map<byte[], byte[]> encodeMap(final Map<String, Serializable> values) {
        if (MapUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }
        Map<String, Serializable> copy = new HashMap<String, Serializable>(values);
        Iterator<Map.Entry<String, Serializable>> iterator = copy.entrySet().iterator();
        Map<byte[], byte[]> ret = new HashMap<byte[], byte[]>();
        while (iterator.hasNext()) {
            Map.Entry<String, Serializable> next = iterator.next();
            ret.put(SafeEncoder.encode(next.getKey()), serialize(next.getValue()));
        }

        return ret;
    }

    /**
     * set map value to target set
     *
     * @param key    cache key
     * @param values map value
     * @throws Exception in case of access redis server exception
     */
    public void hmSet(String key, Map<String, Serializable> values) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.hmset(SafeEncoder.encode(key), encodeMap(values));
            LOGGER.info("hmSet key:" + key + " field:" + values.keySet());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * encode array
     *
     * @param array string in array
     * @return encode value
     */
    private byte[][] encodeArray(final String[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return new byte[0][0];
        }
        int len = array.length;
        List<byte[]> list = new ArrayList<byte[]>(len);
        for (int i = 0; i < len; i++) {
            list.add(SafeEncoder.encode(array[i]));
        }
        return list.toArray(new byte[len][0]);
    }

    /**
     * get keys from set
     *
     * @param key    key name
     * @param fields key names
     * @return cache value list
     * @throws Exception in case of access redis server exception
     */
    public List<Object> hmGet(String key, String... fields) throws Exception {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            List<byte[]> hmget = jedis.hmget(SafeEncoder.encode(key), encodeArray(fields));
            LOGGER.info("hmGet key:" + key + " fields:" + Arrays.toString(fields));
            if (CollectionUtils.isEmpty(hmget)) {
                return new ArrayList<Object>(1);
            } else {
                List<Object> ret = new ArrayList<Object>(hmget.size());
                for (byte[] bb : hmget) {
                    ret.add(deserialize(bb));
                }
                return ret;
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            this.jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                this.jedisPool.returnResource(jedis);
            }
        }
    }

    public void destroy() throws Exception {
        shutdown();
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    protected void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    protected void setRealPool(GenericObjectPool realPool) {
        this.realPool = realPool;
    }

    protected GenericObjectPool getRealPool() {
        return realPool;
    }

    public String getRedisServer() {
        return redisServer;
    }

    public void setRedisServer(String redisServer) {
        this.redisServer = redisServer;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getRedisAuthKey() {
        return redisAuthKey;
    }

    public void setRedisAuthKey(String redisAuthKey) {
        this.redisAuthKey = redisAuthKey;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public byte getWhenExhaustedAction() {
        return whenExhaustedAction;
    }

    public void setWhenExhaustedAction(byte whenExhaustedAction) {
        this.whenExhaustedAction = whenExhaustedAction;
    }

}
