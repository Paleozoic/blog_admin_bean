package com.paleo.blog.model.sys.account;

import com.paleo.blog.tools.http.resource.WebResource;


public class AccountUtils {
	
	private final static String USER_ID_SESSION_KEY = "session:com.paleo.blog.model.account.UserId";
	
	/**
	 * 从会话中取出userId
	 * @return
	 */
	public static Long getUserId() {
		Object obj = WebResource.session().getAttribute(USER_ID_SESSION_KEY);
		if (obj != null && obj.getClass() == Long.class) {
			return (Long)obj;
		} else {
			return null;
		}
	}
	
	/**
	 * 将userId加入会话
	 * @return
	 */
	public static void setUserId(Long userId) {
		WebResource.session().setAttribute(USER_ID_SESSION_KEY, userId);
	}
}
