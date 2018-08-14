package com.yaic.fa.redis.callback;

import redis.clients.jedis.ShardedJedis;

/**
 * <p>Author:lujicong
 * <p>Date: 2015-12-9
 * <p>Version: 1.0
 */
public abstract class RedisNoResultCall implements RedisCallback<Object>{

	public Object doInRedis(ShardedJedis shardedJedis) throws Exception {
		action(shardedJedis);
		return null;
	}
	
	public abstract void action(ShardedJedis shardedJedis);

}
