package com.paleo.blog.cache.redis.utils;

public interface BasicTemplate {
	
	boolean set(String key,String value,JedisCallback<Boolean> action);

	String get(String key, JedisCallback<String> action);
}
