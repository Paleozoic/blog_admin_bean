package com.paleo.blog.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要淘汰的缓存
 * @author Paleo
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheEvict {
	/**
	 * 缓存命名空间，如果想清空多个命名空间，请使用Caching注解
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
	 * 缓存后的逻辑/SpEL
	 * @return
	 */
	String afterCache() default "";
	/**
	 * 是否清空该命名空间下所有缓存
	 * @return
	 */
	boolean allEntries() default false;
	/**
	 * 匹配模式，通过模糊的key匹配，淘汰缓存
	 * @return
	 */
	boolean patternMode() default false;

}
