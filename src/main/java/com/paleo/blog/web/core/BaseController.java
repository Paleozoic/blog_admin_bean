package com.paleo.blog.web.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.web.context.WebApplicationContext;

import com.paleo.blog.tools.http.resource.WebResource;

public class BaseController {
	
	
	/**
	 * 获得响应
	 * @return
	 */
	public  HttpServletRequest req(){
		return WebResource.request();
	}
	
	/**
	 * 获得请求
	 * @return
	 */
	public HttpServletResponse res(){
		return WebResource.response();
	}
		
	/**
	 * 获得web上下文
	 * @return
	 */
	public  WebApplicationContext cxt() {
		return WebResource.webcontext();
	}
	
	/**
	 * 获得会话
	 * @return
	 */
	public  Session session() {
		return WebResource.session();
	}
	
	
}
