package com.paleo.blog.shiro;

import com.paleo.blog.shiro.chain.imp.SimpleFilterChainDefinitionsService;
import com.paleo.blog.tools.spring.SpringContextHolder;
/**
 * 用来更新Shiro的拦截权限，在缓存中的SpEL表达式使用
 * @author Paleo
 *
 */
public class updatePermission {
	public void update(){
		SimpleFilterChainDefinitionsService chainService = (SimpleFilterChainDefinitionsService) SpringContextHolder.getBean("filterChainDefinitionsService");
		chainService.updatePermission();
	}
}
