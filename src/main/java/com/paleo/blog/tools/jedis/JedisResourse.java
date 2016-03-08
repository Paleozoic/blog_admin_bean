package com.paleo.blog.tools.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisResourse {
	
	private static JedisPool jedisPool;
	
	public static Jedis getResource() {
		return jedisPool.getResource();
	}

	public void setJedisPool(JedisPool jedisPool) {
		JedisResourse.jedisPool = jedisPool;
	}

}
