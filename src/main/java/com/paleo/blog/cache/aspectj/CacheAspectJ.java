package com.paleo.blog.cache.aspectj;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.paleo.blog.cache.annotation.parser.PaleoCacheAnnotationParser;
import com.paleo.blog.cache.cache.Cache;
import com.paleo.blog.cache.cache.Cache.ValueWrapper;
import com.paleo.blog.cache.operation.CacheEvictOperation;
import com.paleo.blog.cache.operation.CachePutOperation;
import com.paleo.blog.cache.operation.CacheableOperation;
import com.paleo.blog.cache.operation.basic.CacheOperation;
import com.paleo.blog.cache.redis.RedisCacheManager;
import com.paleo.blog.cache.serializer.Jackson2JsonRedisSerializer;
import com.paleo.blog.cache.tools.generator.StringKeyGenerator;
import com.paleo.blog.cache.tools.spel.SpELUtils;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public class CacheAspectJ {

	private RedisCacheManager cacheManager;
	
	private StringKeyGenerator keyGenerator = new StringKeyGenerator();

	private PaleoCacheAnnotationParser annotationParser = new PaleoCacheAnnotationParser();
	/**
	 * 缓存命中数
	 */
	private static Long cacheHit=0l;
	/**
	 * 取缓存不命中数
	 */
	private static Long cacheMiss=0l;

	@SuppressWarnings("unchecked")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature m = (MethodSignature) joinPoint.getSignature();
		Method method = m.getMethod();

		Collection<CacheOperation> ops = annotationParser.parseCacheAnnotations(method);
		/**
		 * 没有注解，直接返回结果
		 */
		if (ops == null || ops.size() == 0) {
			return joinPoint.proceed();
		}
		/**
		 * 返回值是否已经计算，即是否执行了joinPoint.proceed();
		 */
		boolean rvFlag = false;
		
		Object returnValue = null;

		Object[] args = joinPoint.getArgs();
		/**
		 * TODO 
		 * 1. afterCache未做处理
		 * 2. Cacheable/CachePut value未做处理  (finished)
		 * 3. CacheEvict allEntries patternMode(finished)
		 */
		for (CacheOperation op : ops) {
			if (!op.getBeforeCondition().equals("")) {
				boolean beforeCondition = beforeCondition(op.getBeforeCondition(), args);
				if(!beforeCondition){
					continue;
				}
			}
			if (!op.getAfterCondition().equals("")) {
				if(!rvFlag){
					rvFlag = true;
					returnValue = joinPoint.proceed();
				}
				boolean afterCondition = afterCondition(op.getAfterCondition(), args,returnValue);
				if(!afterCondition){
					continue;
				}
			}

			String cacheKey = null;
			if (op.getKey() != null && op.getKey().length()>0) {//通过spel生成key
				cacheKey = keyGenerator.generateSpEL(joinPoint.getTarget(), method, op.getKey(), args);
			}else{//如果key为空，生成默认key。
				cacheKey = keyGenerator.generate(joinPoint.getTarget(), method, args);
			}
			
			if(op instanceof CachePutOperation){
				if(!rvFlag){
					rvFlag = true;
					returnValue = joinPoint.proceed();
				}
				int expire = ((CachePutOperation)op).getExpire();
				for (String cacheName : op.getCacheNames()) {
					Cache cache = cacheManager.getCache(cacheName);
					if(returnValue!=null){
						cache.put(cacheKey,returnValue,expire);
					}else{
						throw new Exception("Error:In CachePut, the returnValue must not be null!");
					}
				}
			}else if(op instanceof CacheableOperation){
				for (String cacheName : op.getCacheNames()) {
					Cache cache = cacheManager.getCache(cacheName);
					ValueWrapper wrapper = cache.get(cacheKey);
					String cacheValue = (String)wrapper.get();
					if (cacheValue == null) {//缓存为空
						synchronized (cacheMiss) {
							cacheMiss++;
						}
						if(!rvFlag){
							rvFlag = true;
							returnValue = joinPoint.proceed();
						}
						if (returnValue != null) {//不为空才放入缓存
							int expire = ((CacheableOperation)op).getExpire();
							cache.put(cacheKey,returnValue,expire);
						}
					}else{
						synchronized (cacheHit) {
							cacheHit++;
						}
						Class<Object> returnType = m.getReturnType();
						Jackson2JsonRedisSerializer<Object> serializer;
						if(Collection.class.isAssignableFrom(returnType)) {
							ParameterizedTypeImpl type = (ParameterizedTypeImpl)method.getGenericReturnType();
							Type[] typeArr = type.getActualTypeArguments();
							Class<Object> classType = (Class<Object>) typeArr[0];
							serializer = new Jackson2JsonRedisSerializer<>(classType);
							returnValue = serializer.deserializeList(cacheValue);
						}else{
							serializer = new Jackson2JsonRedisSerializer<>(returnType);
							returnValue = serializer.deserialize(cacheValue);
						}
					}
				}
			}else if(op instanceof CacheEvictOperation){
				for (String cacheName : op.getCacheNames()) {
					Cache cache = cacheManager.getCache(cacheName);
					if(((CacheEvictOperation) op).isAllEntries()){
						//清空cacheName下所有缓存
						cache.clear();
					}else{
						if(((CacheEvictOperation) op).isPatternMode()){
							//根据pattern清除
							cache.clearByPatternKey(op.getKey());
						}else{
							//淘汰一个key的缓存
							cache.evict(cacheKey);
						}
					}
				}
				//清除了缓存，执行原方法
				if(!rvFlag){
					rvFlag = true;
					returnValue = joinPoint.proceed();
				}
			}
			/**
			 * 缓存后的操作
			 */
			String afterCache = op.getAfterCache();
			if(afterCache!=null&&afterCache.length()>0){
				afterCache(afterCache,args,returnValue);
			}
		}

		return returnValue;
	}

	
	private boolean beforeCondition(String spel,Object[] args) {
		return SpELUtils.getElValue(spel, args, Boolean.class);
	}
	private boolean afterCondition(String spel,Object[] args,Object returnVal) {
		return SpELUtils.getElValue(spel, args, returnVal, Boolean.class);
	}
	private Void afterCache(String spel,Object[] args,Object returnVal) {
		return SpELUtils.getElValue(spel, args, returnVal, Void.class);
	}

	public void setCacheManager(RedisCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public static Long getCacheHit(){
		return CacheAspectJ.cacheHit;
	}

	public static Long getCacheMiss(){
		return CacheAspectJ.cacheMiss;
	}
}
