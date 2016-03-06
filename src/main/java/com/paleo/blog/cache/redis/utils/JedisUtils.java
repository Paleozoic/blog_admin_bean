package com.paleo.blog.cache.redis.utils;

import java.util.Set;

import redis.clients.jedis.Jedis;


/**
 * JedisUtils
 * 在一个方法里只需调用一次Jedis时使用
 * @author Paleo
 */
public class JedisUtils {
	
	public static Long hset( Jedis jedis, String key,  String field, String value){
		Long resp;
		try {
			 resp = jedis.hset(key, field, value);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return resp;
	}
	
	public static boolean set( Jedis jedis, String key, String value){
		String resp = null ;
		try {
			 resp = jedis.set(key, value);
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return RespCode.OK.getResp().equals(resp);
	}

	public static boolean setex(Jedis jedis, String key, String value, int expire) {
		String resp ;
		try {
			 resp = jedis.setex(key, expire, value);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return RespCode.OK.getResp().equals(resp);
	}

	public static String get(Jedis jedis, String key) {
		String resp = null ;
		try {
			 resp = jedis.get(key);
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return resp;
	}
	
	public static Long del(Jedis jedis, String... keys) {
		Long resp = null ;
		try {
			 resp = jedis.del(keys);
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return resp;
	}
	
	public static Long delByPattern(Jedis jedis, String keyPattern) {
		Long resp = null ;
		try {
			 Set<String> keySet = jedis.keys(keyPattern);
//			 resp = jedis.del((String[]) keySet.toArray());
			 String[] keys = new String[]{};
			 String[] keysArr = keySet.toArray(keys);
			 resp = jedis.del(keysArr);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return resp;
	}
}