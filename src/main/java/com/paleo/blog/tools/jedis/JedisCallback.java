package com.paleo.blog.tools.jedis;

import redis.clients.jedis.Jedis;

public interface JedisCallback<T> {
	
	  T doInJedis(Jedis jedis);
}

