package com.paleo.blog.cache.annotation.parser;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.ObjectUtils;

import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.cache.annotation.CachePut;
import com.paleo.blog.cache.annotation.Cacheable;
import com.paleo.blog.cache.annotation.Caching;
import com.paleo.blog.cache.operation.CacheEvictOperation;
import com.paleo.blog.cache.operation.CachePutOperation;
import com.paleo.blog.cache.operation.CacheableOperation;
import com.paleo.blog.cache.operation.basic.CacheOperation;

public class PaleoCacheAnnotationParser implements CacheAnnotationParser,Serializable {

	private static final long serialVersionUID = -7910136759066990350L;
	
	private Map<String, Collection<CacheOperation>> map = new ConcurrentHashMap<String, Collection<CacheOperation>>();

	public Collection<CacheOperation> parseCacheAnnotations(AnnotatedElement ae) {

		Collection<CacheOperation> ops = map.get(ae.toString());
		if (ops != null) {
			return ops;
		}
		Collection<CacheEvict> evicts = getAnnotations(ae, CacheEvict.class);
		if (evicts != null) {
			ops = lazyInit(ops);
			for (CacheEvict evict : evicts) {
				ops.add(parseEvictAnnotation(ae, evict));
			}
		}
		Collection<CachePut> puts = getAnnotations(ae, CachePut.class);
		if (puts != null) {
			ops = lazyInit(ops);
			for (CachePut put : puts) {
				ops.add(parsePutAnnotation(ae, put));
			}
		}

		Collection<Cacheable> cacheables = getAnnotations(ae, Cacheable.class);
		if (cacheables != null) {
			ops = lazyInit(ops);
			for (Cacheable cacheable : cacheables) {
				ops.add(parseCacheableAnnotation(ae, cacheable));
			}
		}

		Collection<Caching> cachings = getAnnotations(ae, Caching.class);
		if (cachings != null) {
			ops = lazyInit(ops);
			for (Caching caching : cachings) {
				ops.addAll(parseCachingAnnotation(ae, caching));
			}
		}

		if(ops==null) {
			return ops;
		}
		map.put(ae.toString(), ops);
		return ops;
	}

	private <T extends Annotation> Collection<CacheOperation> lazyInit(
			Collection<CacheOperation> ops) {
		return (ops != null ? ops : new ArrayList<CacheOperation>(1));
	}

	CacheableOperation parseCacheableAnnotation(AnnotatedElement ae,
			Cacheable cacheable) {
		CacheableOperation op = new CacheableOperation();
		op.setAfterCache(cacheable.afterCache());
		op.setAfterCondition(cacheable.afterCondition());
		op.setBeforeCondition(cacheable.beforeCondition());
		op.setCacheName(cacheable.cacheName());
		op.setName(ae.toString());
		op.setKey(cacheable.key());
		op.setExpire(cacheable.expire());
		return op;
	}
	
	CacheEvictOperation parseEvictAnnotation(AnnotatedElement ae,
			CacheEvict cacheEvict) {
		CacheEvictOperation op = new CacheEvictOperation();
		op.setAfterCache(cacheEvict.afterCache());
		op.setAfterCondition(cacheEvict.afterCondition());
		op.setBeforeCondition(cacheEvict.beforeCondition());
		op.setCacheName(cacheEvict.cacheName());
		op.setName(ae.toString());
		op.setKey(cacheEvict.key());
		
		op.setAllEntries(cacheEvict.allEntries());
		op.setPatternMode(cacheEvict.patternMode());
		return op;
	}

	CachePutOperation parsePutAnnotation(AnnotatedElement ae,
			CachePut cachePut) {
		CachePutOperation op = new CachePutOperation();
		op.setAfterCache(cachePut.afterCache());
		op.setAfterCondition(cachePut.afterCondition());
		op.setBeforeCondition(cachePut.beforeCondition());
		op.setCacheName(cachePut.cacheName());
		op.setName(ae.toString());
		op.setKey(cachePut.key());
		op.setExpire(cachePut.expire());
		return op;
	}



	Collection<CacheOperation> parseCachingAnnotation(AnnotatedElement ae,
			Caching caching) {
		Collection<CacheOperation> ops = null;

		CacheEvict[] evicts = caching.evict();
		if (!ObjectUtils.isEmpty(evicts)) {
			ops = lazyInit(ops);
			for (CacheEvict evict : evicts) {
				ops.add(parseEvictAnnotation(ae, evict));
			}
		}
		CachePut[] puts = caching.put();
		if (!ObjectUtils.isEmpty(puts)) {
			ops = lazyInit(ops);
			for (CachePut put : puts) {
				ops.add(parsePutAnnotation(ae, put));
			}
		}
		Cacheable[] cacheables = caching.cacheable();
		if (!ObjectUtils.isEmpty(cacheables)) {
			ops = lazyInit(ops);
			for (Cacheable cacheable : cacheables) {
				ops.add(parseCacheableAnnotation(ae, cacheable));
			}
		}

		return ops;
	}

	private static <T extends Annotation> Collection<T> getAnnotations(
			AnnotatedElement ae, Class<T> annotationType) {
		Collection<T> anns = new ArrayList<T>(2);

		// look at raw annotation
		T ann = ae.getAnnotation(annotationType);
		if (ann != null) {
			anns.add(ann);
		}

		// scan meta-annotations
		for (Annotation metaAnn : ae.getAnnotations()) {
			ann = metaAnn.annotationType().getAnnotation(annotationType);
			if (ann != null) {
				anns.add(ann);
			}
		}

		return (anns.isEmpty() ? null : anns);
	}

	
	/**
	 * TODO 待实现
	 */
	@Override
	public Collection<CacheOperation> parseCacheAnnotations(Class<?> type) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * TODO 待实现
	 */
	@Override
 	public Collection<CacheOperation> parseCacheAnnotations(Method method) {
		AnnotatedElement ae = method;
 		return parseCacheAnnotations(ae);
 	}
	
}
