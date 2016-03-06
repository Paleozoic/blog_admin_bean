
package com.paleo.blog.cache.tools.generator;

import java.lang.reflect.Method;

public interface KeyGenerator {
	/**
	 * 默认的生成key方法
	 * @param target
	 * @param method
	 * @param params
	 * @return
	 */
	String generate(Object target, Method method, Object... params);
	/**
	 * 根据SpEL生成key
	 * @param target
	 * @param method
	 * @param spel
	 * @param params
	 * @return
	 */
	String generateSpEL(Object target, Method method, String spel,Object... params);

}