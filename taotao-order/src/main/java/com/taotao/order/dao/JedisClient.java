package com.taotao.order.dao;

/**
 * 接口
 * 为了使jedis单机版和集群版的代码通用, 给单机版和集群版分别定义接口
 * Created by zh on 2/28/2017.
 */
public interface JedisClient {

    String get(String key);

    String set(String key, String value);

    String hget(String hkey, String key);

    long hset(String hkey, String key, String value);

    long incr(String key);

    long expire(String key, int second);

    long ttl(String key);

    long del(String key);

    long hdel(String hkey, String key);

}
