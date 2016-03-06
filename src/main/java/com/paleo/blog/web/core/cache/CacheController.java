package com.paleo.blog.web.core.cache;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paleo.blog.cache.aspectj.CacheAspectJ;

@RequestMapping("/core/cache/")
@Controller("blog.web.core.cache.ctrl.CacheController")
public class CacheController {

	@RequestMapping("cache")
	public String permission(ModelMap rps) throws Exception{
		long cacheHit = CacheAspectJ.getCacheHit();
		long cacheMiss = CacheAspectJ.getCacheMiss();
		rps.put("cacheHit", cacheHit);
		rps.put("cacheMiss", cacheMiss);
		return "_common/core/cache/cache";
	}
	
}
