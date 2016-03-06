package com.paleo.blog.web.core.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.paleo.blog.model.sys.account.AccountUtils;
import com.paleo.blog.remote.sys.account.IAccountService;
import com.paleo.blog.tools.mvc.ctrl.OPT;

/**
 * 登陆拦截器，存储一些账号信息进session
 * @author Paleo
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Resource(name="blog.service.sys.account.imp.AccountService")
	IAccountService accountService;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		if(req.getSession()!=null){
			return true;
		}else{
			req.getRequestDispatcher("/").forward(req, res);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
			throws Exception {
		//shiro登陆会进入拦截器3次，莫名其妙啊！
		//找到原因：登陆1次（LoginController的login），登陆后重定向1次（IndexController的index），还有一次没想到：可能是Shiro内部
		//System.out.println(1);
		
		//如果是登陆操作切登陆成功，将登陆的账号信息加入session。以后将session写入redis，db数据更新的时候同步缓存，同步session
		if(SecurityUtils.getSubject().isAuthenticated()&&OPT.LOGIN.getOpt().equals(req.getParameter("opt"))){
			Long userId = (Long) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();//这里为什么可以取到userId？看LoginRealm源码
//			AccountUtils.addSession(accountService.getAccount(userId));//废弃将account加入Session，因为account信息不能即时更新
			AccountUtils.setUserId(userId);
		}
	}
	
}