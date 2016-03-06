package com.paleo.blog.web.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.web.context.WebApplicationContext;

import com.paleo.blog.tools.http.resource.WebResource;
import com.paleo.blog.tools.mvc.ctrl.OPT;

public class BaseController {
	
	public final static String OPT_NAME = "opt";
	
	/**
	 * 判断请求中 OPT_NAME 参数的值是否为指定的值
	 * @param order
	 * @return
	 */
	public  static boolean IsOPT(OPT opt) {
		if (opt.getOpt()!=null&&opt.getOpt().equals(WebResource.request().getParameter(OPT_NAME))) {
			return true;
		}
		return false;
	}
	
	public  static boolean IsNotOPT(OPT opt) {
		return !IsOPT(opt);
	}
	
	
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
