package com.paleo.blog.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 必定会执行方法，然后再写缓存
 * @author Paleo
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CachePut {
	/**
	 * 缓存命名空间
	 * @return
	 */
	String cacheName();

	/**
	 * 缓存key，支持SpEL，为空使用默认的key generator
	 * @return
	 */
	String key() default "";
	/**
	 * 方法执行前的条件/SpEL
	 * @return
	 */
	String beforeCondition() default "";
	/**
	 * 方法执行后的条件/SpEL
	 * @return
	 */
	String afterCondition() default "";
	/**
	 * 缓存值，默认为空，即缓存方法返回值
	 * @return
	 */
	String value() default "";
	/**
	 * 缓存后的逻辑/SpEL
	 * @return
	 */
	String afterCache() default "";
	/**
	 * 缓存有效时间，0表示无限期
	 * @return
	 */
	int expire() default 0;

}
