/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.cache.redis.cluster;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis集群客户端
 * <p>
 * a example:
 * <p>
 * <pre>
 * {@code
 * <bean id="redisClusterClient" class="com.github.fartherp.framework.cache.redis.cluster.RedisClusterClient">
 *     <property name="jedisCluster" value="jedisCluster" />
 *     <property name="expire" value="30000" />
 * </bean>
 *
 * }
 * </pre>
 *
 * @author: CK
 * @date: 2017/12/27
 */
public class RedisClusterClient {
    private int expire = 0;
    private JedisCluster jedisCluster;

    public String set(String key, String value) {
        jedisCluster.set(key, value);
        if (this.expire != 0) {
            this.expire(key, this.expire);
        }
        return value;
    }

    public String set(String key, String value, String nxxx, String expx, long time) {
        jedisCluster.set(key, value, nxxx, expx, time);
        if (this.expire != 0) {
            this.expire(key, this.expire);
        }
        return value;
    }

    public String get(String key) {
        return jedisCluster.get(key);
    }

    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    public Long exists(String... keys) {
        return jedisCluster.exists(keys);
    }

    public Long persist(String key) {
        return jedisCluster.persist(key);
    }

    public String type(String key) {
        return jedisCluster.type(key);
    }

    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key, seconds);
    }

    public Long pexpire(String key, long milliseconds) {
        return jedisCluster.pexpire(key, milliseconds);
    }

    public Long expireAt(String key, long unixTime) {
        return jedisCluster.expireAt(key, unixTime);
    }

    public Long pexpireAt(String key, long millisecondsTimestamp) {
        return jedisCluster.pexpireAt(key, millisecondsTimestamp);
    }

    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    public Long pttl(String key) {
        return jedisCluster.pttl(key);
    }

    public Boolean setbit(String key, long offset, boolean value) {
        return jedisCluster.setbit(key, offset, value);
    }

    public Boolean setbit(String key, long offset, String value) {
        return jedisCluster.setbit(key, offset, value);
    }

    public Boolean getbit(String key, long offset) {
        return jedisCluster.getbit(key, offset);
    }

    public Long setrange(String key, long offset, String value) {
        return jedisCluster.setrange(key, offset, value);
    }

    public String getrange(String key, long startOffset, long endOffset) {
        return jedisCluster.getrange(key, startOffset, endOffset);
    }

    public String getSet(String key, String value) {
        return jedisCluster.getSet(key, value);
    }

    public Long setnx(String key, String value) {
        return jedisCluster.setnx(key, value);
    }

    public String setex(String key, int seconds, String value) {
        return jedisCluster.setex(key, seconds, value);
    }

    public String psetex(String key, long milliseconds, String value) {
        return jedisCluster.psetex(key, milliseconds, value);
    }

    public Long decrBy(String key, long integer) {
        return jedisCluster.decrBy(key, integer);
    }

    public Long decr(String key) {
        return jedisCluster.decr(key);
    }

    public Long incrBy(String key, long integer) {
        return jedisCluster.incrBy(key, integer);
    }

    public Double incrByFloat(String key, double value) {
        return jedisCluster.incrByFloat(key, value);
    }

    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    public Long append(String key, String value) {
        return jedisCluster.append(key, value);
    }

    public String substr(String key, int start, int end) {
        return jedisCluster.substr(key, start, end);
    }

    public Long hset(String key, String field, String value) {
        return jedisCluster.hset(key, field, value);
    }

    public String hget(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    public Long hsetnx(String key, String field, String value) {
        return jedisCluster.hsetnx(key, field, value);
    }

    public String hmset(String key, Map<String, String> hash) {
        return jedisCluster.hmset(key, hash);
    }

    public List<String> hmget(String key, String... fields) {
        return jedisCluster.hmget(key, fields);
    }

    public Long hincrBy(String key, String field, long value) {
        return jedisCluster.hincrBy(key, field, value);
    }

    public Double hincrByFloat(String key, String field, double value) {
        return jedisCluster.hincrByFloat(key, field, value);
    }

    public Boolean hexists(String key, String field) {
        return jedisCluster.hexists(key, field);
    }

    public Long hdel(String key, String... field) {
        return jedisCluster.hdel(key, field);
    }

    public Long hlen(String key) {
        return jedisCluster.hlen(key);
    }

    public Set<String> hkeys(String key) {
        return jedisCluster.hkeys(key);
    }

    public List<String> hvals(String key) {
        return jedisCluster.hvals(key);
    }

    public Map<String, String> hgetAll(String key) {
        return jedisCluster.hgetAll(key);
    }

    public Long rpush(String key, String... string) {
        return jedisCluster.rpush(key, string);
    }

    public Long lpush(String key, String... string) {
        return jedisCluster.lpush(key, string);
    }

    public Long llen(String key) {
        return jedisCluster.llen(key);
    }

    public List<String> lrange(String key, long start, long end) {
        return jedisCluster.lrange(key, start, end);
    }

    public String ltrim(String key, long start, long end) {
        return jedisCluster.ltrim(key, start, end);
    }

    public String lindex(String key, long index) {
        return jedisCluster.lindex(key, index);
    }

    public String lset(String key, long index, String value) {
        return jedisCluster.lset(key, index, value);
    }

    public Long lrem(String key, long count, String value) {
        return jedisCluster.lrem(key, count, value);
    }

    public String lpop(String key) {
        return jedisCluster.lpop(key);
    }

    public String rpop(String key) {
        return jedisCluster.rpop(key);
    }

    public Long sadd(String key, String... member) {
        return jedisCluster.sadd(key, member);
    }

    public Set<String> smembers(String key) {
        return jedisCluster.smembers(key);
    }

    public Long srem(String key, String... member) {
        return jedisCluster.srem(key, member);
    }

    public String spop(String key) {
        return jedisCluster.spop(key);
    }

    public Set<String> spop(String key, long count) {
        return jedisCluster.spop(key, count);
    }

    public Long scard(String key) {
        return jedisCluster.scard(key);
    }

    public Boolean sismember(String key, String member) {
        return jedisCluster.sismember(key, member);
    }

    public String srandmember(String key) {
        return jedisCluster.srandmember(key);
    }

    public List<String> srandmember(String key, int count) {
        return jedisCluster.srandmember(key, count);
    }

    public Long strlen(String key) {
        return jedisCluster.strlen(key);
    }

    public Long zadd(String key, double score, String member) {
        return jedisCluster.zadd(key, score, member);
    }

    public Long zadd(String key, double score, String member, ZAddParams params) {
        return jedisCluster.zadd(key, score, member, params);
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return jedisCluster.zadd(key, scoreMembers);
    }

    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return jedisCluster.zadd(key, scoreMembers, params);
    }

    public Set<String> zrange(String key, long start, long end) {
        return jedisCluster.zrange(key, start, end);
    }

    public Long zrem(String key, String... member) {
        return jedisCluster.zrem(key, member);
    }

    public Double zincrby(String key, double score, String member) {
        return jedisCluster.zincrby(key, score, member);
    }

    public Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return jedisCluster.zincrby(key, score, member, params);
    }

    public Long zrank(String key, String member) {
        return jedisCluster.zrank(key, member);
    }

    public Long zrevrank(String key, String member) {
        return jedisCluster.zrevrank(key, member);
    }

    public Set<String> zrevrange(String key, long start, long end) {
        return jedisCluster.zrevrange(key, start, end);
    }

    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return jedisCluster.zrangeWithScores(key, start, end);
    }

    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return jedisCluster.zrevrangeWithScores(key, start, end);
    }

    public Long zcard(String key) {
        return jedisCluster.zcard(key);
    }

    public Double zscore(String key, String member) {
        return jedisCluster.zscore(key, member);
    }

    public List<String> sort(String key) {
        return jedisCluster.sort(key);
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        return jedisCluster.sort(key, sortingParameters);
    }

    public Long zcount(String key, double min, double max) {
        return jedisCluster.zcount(key, min, max);
    }

    public Long zcount(String key, String min, String max) {
        return jedisCluster.zcount(key, min, max);
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        return jedisCluster.zrangeByScore(key, min, max);
    }

    public Set<String> zrangeByScore(String key, String min, String max) {
        return jedisCluster.zrangeByScore(key, min, max);
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        return jedisCluster.zrevrangeByScore(key, max, min);
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return jedisCluster.zrangeByScore(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByScore(String key, String max, String min) {
        return jedisCluster.zrevrangeByScore(key, max, min);
    }

    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return jedisCluster.zrangeByScore(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return jedisCluster.zrevrangeByScore(key, max, min, offset, count);
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return jedisCluster.zrevrangeByScoreWithScores(key, max, min);
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return jedisCluster.zrevrangeByScore(key, max, min, offset, count);
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return jedisCluster.zrevrangeByScoreWithScores(key, min, max);
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    public Long zremrangeByRank(String key, long start, long end) {
        return jedisCluster.zremrangeByRank(key, start, end);
    }

    public Long zremrangeByScore(String key, double start, double end) {
        return jedisCluster.zremrangeByScore(key, start, end);
    }

    public Long zremrangeByScore(String key, String start, String end) {
        return jedisCluster.zremrangeByScore(key, start, end);
    }

    public Long zlexcount(String key, String min, String max) {
        return jedisCluster.zlexcount(key, min, max);
    }

    public Set<String> zrangeByLex(String key, String min, String max) {
        return jedisCluster.zrangeByLex(key, min, max);
    }

    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return jedisCluster.zrangeByLex(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByLex(String key, String max, String min) {
        return jedisCluster.zrevrangeByLex(key, max, min);
    }

    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return jedisCluster.zrevrangeByLex(key, max, min, offset, count);
    }

    public Long zremrangeByLex(String key, String min, String max) {
        return jedisCluster.zremrangeByLex(key, min, max);
    }

    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return jedisCluster.linsert(key, where, pivot, value);
    }

    public Long lpushx(String key, String... string) {
        return jedisCluster.lpushx(key, string);
    }

    public Long rpushx(String key, String... string) {
        return jedisCluster.rpushx(key, string);
    }

    public Long del(String key) {
        return jedisCluster.del(key);
    }

    public String echo(String string) {
        // note that it'll be run from arbitary node
        return jedisCluster.echo(string);
    }

    public Long bitcount(String key) {
        return jedisCluster.bitcount(key);
    }

    public Long bitcount(String key, long start, long end) {
        return jedisCluster.bitcount(key, start, end);
    }

    public ScanResult<String> scan(String cursor, ScanParams params) {
        return jedisCluster.scan(cursor, params);
    }

    public Long bitpos(String key, boolean value) {
        return jedisCluster.bitpos(key, value);
    }

    public Long bitpos(String key, boolean value, BitPosParams params) {
        return jedisCluster.bitpos(key, value, params);
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return jedisCluster.hscan(key, cursor);
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return jedisCluster.hscan(key, cursor, params);
    }

    public ScanResult<String> sscan(String key, String cursor) {
        return jedisCluster.sscan(key, cursor);
    }

    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        return jedisCluster.sscan(key, cursor, params);
    }

    public ScanResult<Tuple> zscan(String key, String cursor) {
        return jedisCluster.zscan(key, cursor);
    }

    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return jedisCluster.zscan(key, cursor, params);
    }

    public Long pfadd(String key, String... elements) {
        return jedisCluster.pfadd(key, elements);
    }

    public long pfcount(String key) {
        return jedisCluster.pfcount(key);
    }

    public List<String> blpop(int timeout, String key) {
        return jedisCluster.blpop(timeout, key);
    }

    public List<String> brpop(int timeout, String key) {
        return jedisCluster.brpop(timeout, key);
    }

    public Long del(String... keys) {
        return jedisCluster.del(keys);
    }

    public List<String> blpop(int timeout, String... keys) {
        return jedisCluster.blpop(timeout, keys);

    }

    public List<String> brpop(int timeout, String... keys) {
        return jedisCluster.brpop(timeout, keys);
    }

    public List<String> mget(String... keys) {
        return jedisCluster.mget(keys);
    }

    public String mset(String... keysvalues) {
        return jedisCluster.mset(keysvalues);
    }

    public Long msetnx(String... keysvalues) {
        return jedisCluster.msetnx(keysvalues);
    }

    public String rename(String oldkey, String newkey) {
        return jedisCluster.rename(oldkey, newkey);
    }

    public Long renamenx(String oldkey, String newkey) {
        return jedisCluster.renamenx(oldkey, newkey);
    }

    public String rpoplpush(String srckey, String dstkey) {
        return jedisCluster.rpoplpush(srckey, dstkey);
    }

    public Set<String> sdiff(String... keys) {
        return jedisCluster.sdiff(keys);
    }

    public Long sdiffstore(String dstkey, String... keys) {
        return jedisCluster.sdiffstore(dstkey, keys);
    }

    public Set<String> sinter(String... keys) {
        return jedisCluster.sinter(keys);
    }

    public Long sinterstore(String dstkey, String... keys) {
        return jedisCluster.sinterstore(dstkey, keys);
    }

    public Long smove(String srckey, String dstkey, String member) {
        return jedisCluster.smove(srckey, dstkey, member);
    }

    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        return jedisCluster.sort(key, sortingParameters, dstkey);
    }

    public Long sort(String key, String dstkey) {
        return jedisCluster.sort(key, dstkey);
    }

    public Set<String> sunion(String... keys) {
        return jedisCluster.sunion(keys);
    }

    public Long sunionstore(String dstkey, String... keys) {
        return jedisCluster.sunionstore(dstkey, keys);
    }

    public Long zinterstore(String dstkey, String... sets) {
        return jedisCluster.zinterstore(dstkey, sets);
    }

    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        return jedisCluster.zinterstore(dstkey, params, sets);
    }

    public Long zunionstore(String dstkey, String... sets) {
        return jedisCluster.sunionstore(dstkey, sets);
    }

    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        return jedisCluster.zunionstore(dstkey, params, sets);
    }

    public String brpoplpush(String source, String destination, int timeout) {
        return jedisCluster.brpoplpush(source, destination, timeout);
    }

    public Long publish(String channel, String message) {
        return jedisCluster.publish(channel, message);
    }

    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        jedisCluster.subscribe(jedisPubSub, channels);
    }

    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        jedisCluster.psubscribe(jedisPubSub, patterns);
    }

    public Long bitop(BitOP op, String destKey, String... srcKeys) {
        return jedisCluster.bitop(op, destKey, srcKeys);
    }

    public String pfmerge(String destkey, String... sourcekeys) {
        return jedisCluster.pfmerge(destkey, sourcekeys);
    }

    public long pfcount(String... keys) {
        return jedisCluster.pfcount(keys);
    }

    public Object eval(String script, int keyCount, String... params) {
        return jedisCluster.eval(script, keyCount, params);
    }

    public Object eval(String script, String key) {
        return jedisCluster.eval(script, key);
    }

    public Object eval(String script, List<String> keys, List<String> args) {
        return jedisCluster.eval(script, keys, args);
    }

    public Object evalsha(String sha1, int keyCount, String... params) {
        return jedisCluster.evalsha(sha1, keyCount, params);
    }

    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return jedisCluster.evalsha(sha1, keys, args);
    }

    public Object evalsha(String script, String key) {
        return jedisCluster.evalsha(script, key);
    }

    public Boolean scriptExists(String sha1, String key) {
        return jedisCluster.scriptExists(sha1, key);
    }

    public List<Boolean> scriptExists(String key, String... sha1) {
        return jedisCluster.scriptExists(key, sha1);
    }

    public String scriptLoad(String script, String key) {
        return jedisCluster.scriptLoad(script, key);
    }

    public Long geoadd(String key, double longitude, double latitude, String member) {
        return jedisCluster.geoadd(key, longitude, latitude, member);
    }

    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return jedisCluster.geoadd(key, memberCoordinateMap);
    }

    public Double geodist(String key, String member1, String member2) {
        return jedisCluster.geodist(key, member1, member2);
    }

    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return jedisCluster.geodist(key, member1, member2, unit);
    }

    public List<String> geohash(String key, String... members) {
        return jedisCluster.geohash(key, members);
    }

    public List<GeoCoordinate> geopos(String key, String... members) {
        return jedisCluster.geopos(key, members);
    }

    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
                                             GeoUnit unit) {
        return jedisCluster.georadius(key, longitude, latitude, radius, unit);
    }

    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
                                             GeoUnit unit, GeoRadiusParam param) {
        return jedisCluster.georadius(key, longitude, latitude, radius, unit, param);
    }

    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return jedisCluster.georadiusByMember(key, member, radius, unit);
    }

    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
                                                     GeoRadiusParam param) {
        return jedisCluster.georadiusByMember(key, member, radius, unit, param);
    }

    public List<Long> bitfield(String key, String... arguments) {
        return jedisCluster.bitfield(key, arguments);
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
