package com.yaic.fa.redis.callback;

import redis.clients.jedis.ShardedJedis;

/**
 * <p>Author:lujicong
 * <p>Date: 2015-12-9
 * <p>Version: 1.0
 * @param <T>
 */
public interface RedisCallback<T> {

	T doInRedis(ShardedJedis shardedJedis) throws Exception;
}
