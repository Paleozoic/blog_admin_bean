package com.paleo.blog.cache.redis;

import java.io.Serializable;

import com.paleo.blog.cache.cache.Cache.ValueWrapper;

public class RedisValueWrapper implements ValueWrapper, Serializable {

	private static final long serialVersionUID = 6035835803621444284L;
	
	private Object value;

	public RedisValueWrapper() {

	}

	/**
	 * Create a new SimpleValueWrapper instance for exposing the given value.
	 * 
	 * @param value
	 *            the value to expose (may be {@code null})
	 */
	public RedisValueWrapper(Object value) {
		this.setValue(value);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Simply returns the value as given at construction time.
	 */
	public Object get() {
		return this.value;
	}

}
