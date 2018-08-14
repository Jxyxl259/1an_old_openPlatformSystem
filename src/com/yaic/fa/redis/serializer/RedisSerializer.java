package com.yaic.fa.redis.serializer;

/**
 * <p>Author:lujicong
 * <p>Date: 2015-12-9
 * <p>Version: 1.0
 * @param <T>
 */
public interface RedisSerializer<T> {

	/**
	 * Serialize the given object to binary data.
	 * 
	 * @param t object to serialize
	 * @return the equivalent binary data
	 */
	byte[] serialize(T t) throws RuntimeException;

	/**
	 * Deserialize an object from the given binary data.
	 * 
	 * @param bytes object binary representation
	 * @return the equivalent object instance
	 */
	T deserialize(byte[] bytes) throws RuntimeException;
}
