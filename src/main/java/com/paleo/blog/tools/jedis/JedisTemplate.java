package com.paleo.blog.tools.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
/**
 * Jedis不应该在Utils里面关闭，导致的问题：
 * 不过如果每次都需要close，那如果封装在工具类里，多次调用不就是需要多次close？
 * 如果不封装utils来使用，又要在其他代码里面close，不美观。
 * 如何调和这个问题？
 * 参考模板模式，通过callback关闭jedis：http://www.iteye.com/topic/713770
 * 
 * 
 * 模板方法模式，callback
 * 如果在一个方法内多次调用jedis，需要使用此方法
 * 使用方法：
 * private static JedisTemplate tpl = new JedisTemplate();
 * tpl.set("key", "value", new JedisCallback(Boolean) {
 *			@Override
 *			public boolean doInJedis(Jedis jedis) {
 *				// TODO 这里就是通过Jedis进行你的操作了
 *				//多次jedis调用 code
 *			}
 *			
 *		});
 * 
 * @author Paleo
 *
 */
public  class JedisTemplate implements BasicTemplate{
	
	@Override
	public boolean set(String key,String value,JedisCallback<Boolean> action) {
		Jedis jedis = null;
		try{
			jedis = JedisResourse.getResource();
			return action.doInJedis(jedis);
		}catch(Exception e){
			throw new JedisException(e);
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}



	@Override
	public String get(String key, JedisCallback<String> action) {
		Jedis jedis = null;
		try{
			jedis = JedisResourse.getResource();
			return action.doInJedis(jedis);
		}catch(Exception e){
			throw new JedisException(e);
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
}

