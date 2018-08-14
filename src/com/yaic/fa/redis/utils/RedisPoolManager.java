package com.yaic.fa.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * <p>Author:lujicong
 * <p>Date: 2015-12-9
 * <p>Version: 1.0
 */
public class RedisPoolManager {

	
	private ShardedJedisPool shardedJedisPool;
	
	public RedisPoolManager(ShardedJedisPool shardedJedisPool){
		this.shardedJedisPool = shardedJedisPool;
	}
	

	public ShardedJedis getReids(){
		return shardedJedisPool.getResource();
	}
	
	public ShardedJedisPool getPool(){
		return this.shardedJedisPool;
	}
	
	
	public void close(ShardedJedis shardedJedis){
		if(shardedJedis != null){
			shardedJedis.disconnect();
		}
	}
	
	public Jedis getJedis(byte[] key){
		return getReids().getShard(key);
	}
	
	
	/**
	 * 描述：
	 * <pre>
	 * 收回资源
	 * </pre>
	 * @param shardedJedis
	 * @param recovery
	 */
	public void recovery(ShardedJedis shardedJedis, boolean recovery){
		if(recovery){
			shardedJedisPool.returnBrokenResource(shardedJedis);
		}else{
			shardedJedisPool.returnResource(shardedJedis);
		}
	}
	
	
	/*public boolean ping(ShardedJedis shardedJedis){
		shardedJedis.pipelined().;创建一个分布式ID
	}*/
	

	
}
