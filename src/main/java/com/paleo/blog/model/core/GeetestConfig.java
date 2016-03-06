package com.paleo.blog.model.core;

/**
 * GeetestWeb配置文件
 * 
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
//	private static final String captcha_id = "b46d1900d0a894591916ea94ea91bd2c";
//	private static final String private_key = "36fc3fe98530eea08dfc6ce76e3d24c4";
	/**
	 * 通过Spring注入
	 */
	private static  String captcha_id;
	private static  String private_key;
	
	public static String getCaptcha_id() {
		return captcha_id;
	}
	public void setCaptcha_id(String captcha_id) {
		GeetestConfig.captcha_id = captcha_id;
	}
	public static String getPrivate_key() {
		return private_key;
	}
	public void setPrivate_key(String private_key) {
		GeetestConfig.private_key = private_key;
	}
}