package com.yaic.fa.redis.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.springframework.core.NestedIOException;

/**
 * <p>Author:lujicong
 * <p>Date: 2015-12-9
 * <p>Version: 1.0
 * @param <T>
 */
public class JdkSerializer<T> implements RedisSerializer<T> {

	
	public byte[] serialize(T t) throws RuntimeException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(256);
		try  {
			serialize(t, byteStream);
			return byteStream.toByteArray();
		}
		catch (Throwable ex) {
			ex.printStackTrace();
			throw new RuntimeException("序列化失败");
		}
	}

	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) throws RuntimeException {
	    /** add at 20151215 start **/
	    if(bytes == null) {
	        return null;
	    }
	    /** add at 20151215 end **/
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		try {
			return (T) deserialize(byteStream);
		}
		catch (Throwable ex) {
			throw new RuntimeException("反序列化失败");
		}
	}
	
	
	private void serialize(Object object, OutputStream outputStream) throws IOException {
		if (!(object instanceof Serializable)) {
			throw new IllegalArgumentException(getClass().getSimpleName() + " requires a Serializable payload " +
					"but received an object of type [" + object.getClass().getName() + "]");
		}
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();
	}
	
	private Object deserialize(InputStream inputStream) throws IOException {
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		try {
			return objectInputStream.readObject();
		}
		catch (ClassNotFoundException ex) {
			throw new NestedIOException("Failed to deserialize object type", ex);
		}
	}
	
}
