package com.paleo.blog.web.core.login;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geetest.sdk.java.GeetestLib;
import com.paleo.blog.model.core.GeetestConfig;
import com.paleo.blog.pojo.core.login.Login;
import com.paleo.blog.tools.encrypt.MD5Utils;
import com.paleo.blog.tools.mvc.view.EmptyViewResolver;
import com.paleo.blog.tools.mvc.view.JsonView;
import com.paleo.blog.web.core.BaseController;

@RequestMapping("/core/")
@Controller("blog.web.core.login.ctrl.LoginController")
public class LoginController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("login_view")
	public String login_view(){
		if(SecurityUtils.getSubject().isAuthenticated()){
			return "redirect:/";
		}
		return "_common/core/login/login";
	}
	
	@RequestMapping("login")
	public String login(Login bo){
		try {
	    	if(verify_captcha()){
	    		UsernamePasswordToken token = new UsernamePasswordToken(bo.getUserName(),MD5Utils.customMD5(bo.getPassword()),bo.isRememberMe());
	    		SecurityUtils.getSubject().login(token);
//	            return "redirect:/";//改为前端通过ajax重定向
	    	}else{
	    		JsonView.error(res(), "验证码错误");
	    	}
        } catch (AuthenticationException e) {
        	e.printStackTrace();
        	JsonView.error(res(), e.getMessage());
        }
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping("logout")
	public String logout(){
		SecurityUtils.getSubject().logout();
		return "redirect:/core/login_view";
	}
	
	@RequestMapping("unauthorized")
	public String unauthorized(){
		return "_common/core/login/unauthorized";
	}
	
	/**
	 * 使用Get的方式返回challenge和capthca_id,此方式以实现前后端完全分离的开发模式
	 * @throws IOException 
	 *
	 */
	@RequestMapping("start_captcha")
	@ResponseBody
	public String start_captcha(){
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getCaptcha_id(), GeetestConfig.getPrivate_key());
		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess();
		//将服务器状态设置到session中
		session().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
		return gtSdk.getResponseStr();
	}
	
	private boolean verify_captcha(){
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getCaptcha_id(), GeetestConfig.getPrivate_key());
		String challenge = req().getParameter(GeetestLib.fn_geetest_challenge);
		String validate = req().getParameter(GeetestLib.fn_geetest_validate);
		String seccode = req().getParameter(GeetestLib.fn_geetest_seccode);
		//从session中获取gt-server状态
		int gt_server_status_code = (Integer) session().getAttribute(gtSdk.gtServerStatusSessionKey);
		int gtResult = 0;
		if (gt_server_status_code == 1) {
			//gt-server正常，向gt-server进行二次验证
			gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode);
		} else {
			// gt-server非正常情况下，进行failback模式验证
			log.info("failback:use your own server captcha validate");
			gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
		}

		if (gtResult == 1) {
			// 验证成功
			log.info("success:" + gtSdk.getVersionInfo());
			return true;
		}else {
			// 验证失败
			log.info("fail:" + gtSdk.getVersionInfo());
			return false;
		}
	}
}
