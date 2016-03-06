package com.paleo.blog.cache.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paleo.blog.cache.cache.Cache;
import com.paleo.blog.cache.cache.SimpleCacheManager;

import redis.clients.jedis.JedisPool;

public class RedisCacheManager extends SimpleCacheManager {
	static Logger log = LoggerFactory.getLogger(RedisCacheManager.class);
	
	// fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
    private final Collection<String> names = Collections.unmodifiableSet(caches.keySet());
 
    private  JedisPool jedisPool;
 
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
    
	@Override
    public Cache getCache(String name) {
        Cache cache = caches.get(name);
        if (cache == null) {
        	cache = new RedisCache(name, jedisPool);
            caches.put(name, cache);
        }
        return cache;
    }
 
 
    public Collection<String> getCacheNames() {
        return names;
    }
 
	@Override
	protected Collection<? extends Cache> loadCaches() {
		return new ArrayList<Cache>();
	}

}
