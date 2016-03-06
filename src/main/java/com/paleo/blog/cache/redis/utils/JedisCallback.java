package com.paleo.blog.cache.redis.utils;

import redis.clients.jedis.Jedis;

public interface JedisCallback<T> {
	
	  T doInJedis(Jedis jedis);
}

