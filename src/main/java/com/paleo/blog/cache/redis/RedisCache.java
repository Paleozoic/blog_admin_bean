package com.paleo.blog.cache.redis;

import org.springframework.util.Assert;

import com.paleo.blog.cache.cache.Cache;
import com.paleo.blog.cache.redis.utils.JedisUtils;
import com.paleo.blog.cache.serializer.Jackson2JsonRedisSerializer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCache implements Cache {

	private static Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
	
    private final String name;
    private final JedisPool jedisPool;
    /**
     * TODO 需要判断jedisPool.getResource()是否是线程安全？如果不是需要加锁
     * @return
     */
	public Jedis getResource() {
		return jedisPool.getResource();
	}
	
    /**
     * Constructs a new <code>RedisCache</code> instance.
     * @param name
     * @param jedisPool
     */
    RedisCache(String name, JedisPool jedisPool) {
        Assert.hasText(name, "non-empty cache name is required");
        this.name = name;
        this.jedisPool = jedisPool;
    }
 
    public String getName() {
        return name;
    }
 
 
    public ValueWrapper get(final Object key) {
    	Jedis jedis = getResource();
    	ValueWrapper element = null;
		String cacheValue = JedisUtils.get(jedis,new StringBuffer(name).append(key).toString());
		if(cacheValue==null){
			element = new RedisValueWrapper();
		}else{
			element = new RedisValueWrapper(cacheValue);
		}
		return element;
    }
 
    /**
     * Object key,事实上我在切面只会传String key
     */
    public void put(final Object key, final Object value,final int expire) {
        Jedis jedis = getResource();
        if(expire<=0){
        	JedisUtils.set(jedis, new StringBuffer(name).append(key).toString(), serializer.serialize(value));
        }else{
        	JedisUtils.setex(jedis, new StringBuffer(name).append(key).toString(), serializer.serialize(value),expire);
        }
    }
 
 
	@Override
	public void clearByPatternKey(String keyPattern) {
		Jedis jedis = getResource();
    	JedisUtils.delByPattern(jedis, new StringBuffer(name).append(keyPattern).toString());
	}
	
	@Override
	public void evict(Object key) {
		Jedis jedis = getResource();
    	JedisUtils.del(jedis, new StringBuffer(name).append(key).toString());
	}
	
    @Override
    public void clear() {
    	Jedis jedis = getResource();
    	JedisUtils.delByPattern(jedis, name+"*");
    }
    
	@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
