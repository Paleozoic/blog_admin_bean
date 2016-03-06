package com.paleo.test.tools;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.paleo.blog.cache.exception.SerializationException;
import com.paleo.blog.cache.redis.RedisValueWrapper;
import com.paleo.blog.cache.serializer.Jackson2JsonRedisSerializer;
import com.paleo.blog.tools.json.jackson.JacksonUtils;

public class jackson {
	@Test
	public void test() throws SerializationException, UnsupportedEncodingException{
		String value="1111";
//		String valueString1 = JacksonUtils.obj2Json(new RedisValueWrapper(value));
		Jackson2JsonRedisSerializer<RedisValueWrapper> serializer = new Jackson2JsonRedisSerializer<>(RedisValueWrapper.class);
		String valueString2 = serializer.serialize(new RedisValueWrapper(value));
		serializer.deserialize(valueString2);
		System.out.println(valueString2);
	}
	
}
