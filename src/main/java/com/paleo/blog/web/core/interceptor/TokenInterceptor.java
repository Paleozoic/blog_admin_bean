package com.paleo.blog.web.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.paleo.blog.core.token.annotation.Token;
import com.paleo.blog.core.token.annotation.ValidToken;
import com.paleo.blog.tools.jedis.JedisUtils;
import com.paleo.blog.tools.uuid.UUIDUtils;

/**
 * TOKEN拦截器
 * redis来保存token，由于redis的单线程，还可以线程安全
 * 一开始我打算使用Shiro Session然后里面保存着token set，进行contains remove add操作token的
 * 考虑到线程不安全：http://blog.csdn.net/mylovepan/article/details/38894941
 * 使用蘑菇街方案 incr：http://cailin.iteye.com/blog/2275063
 * 如果是用set get出来token判断，那么get 和 del的代码应该同步。
 * 采用incr则不需要
 * @author Paleo
 *
 */
public class TokenInterceptor implements HandlerInterceptor{

	private final static String TOKEN_NAMESPACE = "_TOKEN_";
	
	private final static String TOKEN = "_token";//前端  name="_token" value="xxx"
	
	private static final int TOKEN_EXPIRE = 30*60;
	
	private static final String INIT_COUNT = "0";
	
	private static final long UNLOCK_COUNT = 1l;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		boolean flag = false;
		if (handler instanceof DefaultServletHttpRequestHandler) {//静态请求不拦截
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //方法注解
        ValidToken validTokenAnn = method.getAnnotation(ValidToken.class);
        if(validTokenAnn!=null){//方法注解不为null，获取方法注解。且不再理会类注解
        	flag = validTokenAnn.token();
        }else{
        	Object bean = handlerMethod.getBean();
        	validTokenAnn = bean.getClass().getAnnotation(ValidToken.class);//类注解
        	if(validTokenAnn!=null){//方法注解为null，则获取类注解
        		flag = validTokenAnn.token();
        	}
        }
        if(flag){//判断是否需要验证token
        	String token = req.getParameter(TOKEN);
        	if(StringUtils.isEmpty(token)){//前端没有提交token
        		return false;
        	}
        	Long count = null;
        	try {
        		count = JedisUtils.incr(token);
			} catch (Exception e) {
				return false;
			}
        	if(count!=null&&count.longValue()==UNLOCK_COUNT){
        		JedisUtils.del(token);//验证通过，清除token
        		return true;
        	}else{
        		return false;
        	}
        }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (handler instanceof HandlerMethod) {
			boolean flag = false;
			HandlerMethod handlerMethod = (HandlerMethod) handler;
	        Method method = handlerMethod.getMethod();
	        //方法注解
	        Token tokenAnn = method.getAnnotation(Token.class);
	        if(tokenAnn!=null){//方法注解不为null，获取方法注解。且不再理会类注解
	        	flag = tokenAnn.token();
	        }else{
	        	Object bean = handlerMethod.getBean();
	        	tokenAnn = bean.getClass().getAnnotation(Token.class);//类注解
	        	if(tokenAnn!=null){//方法注解为null，则获取类注解
	        		flag = tokenAnn.token();
	        	}
	        }
	        if(flag){
	        	String token = UUIDUtils.getUUID();
				String key = generateTokenKey(token);
				req.setAttribute(TOKEN, token);
				JedisUtils.setex(key, INIT_COUNT, TOKEN_EXPIRE);
	        }
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
			throws Exception {
		
	}
	
	private String generateTokenKey(String token){
		return new StringBuffer(TOKEN_NAMESPACE).append(token).toString();
	}
}