package com.paleo.blog.web.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.paleo.blog.model.sys.account.AccountUtils;

/**
 * 登陆拦截器，存储一些账号信息进session
 * @author Paleo
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
			return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
			throws Exception {
		if(SecurityUtils.getSubject().isAuthenticated()){
			Long userId = (Long) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();//这里为什么可以取到userId？看LoginRealm源码
			AccountUtils.setUserId(userId);
		}
	}
	
}