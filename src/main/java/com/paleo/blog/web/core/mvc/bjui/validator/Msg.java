package com.paleo.blog.web.core.mvc.bjui.validator;

/**
 * 根据nice validator 远程验证remote封装的返回结果
 * @author Paleo
 *
 */
public class Msg {
	
	public static String ok(String msg){
		return "{\"ok\":\""+msg+"\"}";
	}
	
	public static String error(String msg){
		return "{\"error\":\""+msg+"\"}";
	}
}
