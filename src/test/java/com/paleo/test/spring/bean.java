package com.paleo.test.spring;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.paleo.blog.tools.spring.SpringContextHolder;
import com.paleo.test.basic.BasicTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class bean extends BasicTest{
	
	@Resource(name="jedisPool")
	private  JedisPool jedisPool;
	
	@SuppressWarnings("unused")
	@Test
	public void test(){
		JedisPool pool = SpringContextHolder.getBean("jedisPool");
		Jedis jedis = pool.getResource();
		Map obj = SpringContextHolder.getBean("props");
		SqlSessionFactory obj2 = SpringContextHolder.getBean("mybatis.sessionFactory");
		System.out.println(obj.get("mybatis.cacheEnabled"));
	}
}
