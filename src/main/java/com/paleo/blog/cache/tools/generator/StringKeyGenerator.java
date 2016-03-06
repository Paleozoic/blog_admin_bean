/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paleo.blog.cache.tools.generator;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.tools.spel.SpELUtils;

public class StringKeyGenerator implements KeyGenerator {

	public static final int NO_PARAM_KEY = 0;

	public static final int NULL_PARAM_KEY = 53;
	
	
	/**
	 * 默认key：packageName.methodName[param1Class][param2Class][params HashCode]
	 * 默认key问题：
	 * （1）哈希碰撞
	 * （2）params如果存在多余的参数，会导致缓存结果一致但是key不同的情况，降低缓存命中
	 */
	@Override
	public String generate(Object target, Method method, Object... params) {
		StringBuffer key = new StringBuffer(target.getClass().getName()).append(CacheNamespace.SPT_DOT).append(method.getName());
		for(Object param:params){
			key.append(CacheNamespace.SPT_LEFT_MID).append(param.getClass().getName()).append(CacheNamespace.SPT_RIGHT_MID)
			.append(CacheNamespace.SPT_LEFT_MID).append(hashCode(params)).append(CacheNamespace.SPT_RIGHT_MID);
		}
		return key.toString();
	}
	/**
	 * 未解决默认key生成策略的问题，可以通过SpEL指定参数值来生成key
	 * packageName.methodName[param1Class][param2Class][spel value HashCode]
	 */
	@Override
	public String generateSpEL(Object target, Method method, String spel, Object... params) {
		StringBuffer key = new StringBuffer(target.getClass().getName()).append(CacheNamespace.SPT_DOT).append(method.getName());
		for(Object param:params){
			key.append(CacheNamespace.SPT_LEFT_MID).append(param.getClass().getName()).append(CacheNamespace.SPT_RIGHT_MID);
		}
		String spelKey = SpELUtils.getElValue(spel, params, String.class);
		key.append(CacheNamespace.SPT_LEFT_MID).append(spelKey.hashCode()).append(CacheNamespace.SPT_RIGHT_MID);
		return key.toString();
	}

	/**
	 * TODO 这里还需要搞懂HashCode和equals的关系，哈希碰撞对key的影响
	 * @param params
	 * @return
	 */
	private int hashCode(Object... params) {
		if (params.length == 0) {
			return NO_PARAM_KEY;
		}
		if (params.length == 1) {
			Object param = params[0];
			if (param == null) {
				return NULL_PARAM_KEY;
			}
			if (!param.getClass().isArray()) {
				return param.hashCode();
			}
		}
		return Arrays.deepHashCode(params);
	}
}