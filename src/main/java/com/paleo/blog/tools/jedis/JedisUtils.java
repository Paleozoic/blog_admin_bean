package com.paleo.blog.tools.jedis;

import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * JedisUtils
 * 在一个方法里只需调用一次Jedis时使用
 * @author Paleo
 */
public class JedisUtils {
	
	
	public static Long hset( String key,  String field, String value){
		Jedis jedis = JedisResourse.getResource();
		Long resp = null;
		try {
			 resp = jedis.hset(key, field, value);
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return resp;
	}
	
	public static boolean set(String key, String value){
		Jedis jedis = JedisResourse.getResource();
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

	public static boolean setex(String key, String value, int expire) {
		Jedis jedis = JedisResourse.getResource();
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

	public static String get(String key) {
		Jedis jedis = JedisResourse.getResource();
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
	
	public static Long del( String... keys) {
		Jedis jedis = JedisResourse.getResource();
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
	
	public static Long delByPattern(String keyPattern) {
		Jedis jedis = JedisResourse.getResource();
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
	
	public static Boolean exists(String key) {
		Jedis jedis = JedisResourse.getResource();
		Boolean resp = null ;
		try {
			 resp = jedis.exists(key);
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return resp;
	}

	public static Long incr(String key) {
		Jedis jedis = JedisResourse.getResource();
		Long resp = null ;
		try {
			 resp = jedis.incr(key);
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return resp;
	}
}