package com.paleo.blog.tools.encrypt;

import java.security.MessageDigest;


public class MD5Utils {

	private final static String DEFAULT_CHARCODE = "utf-8";

	/**
	 *原生MD5匹配字符串
	 */
	private final static char MAPPING_CHAR_DEFAULT[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 *混淆字符串（避免撞库）
	 */
	private final static char MAPPING_CHAR_PLUS[] = { 'a', 'b', 'c', 'd', 'e', 'f','0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 自定义加密规则 1.Base64加密 2.使用md5+混淆匹配字符串加密
	 * @param msg
	 * @return
	 */
	public static String customMD5(String msg) {
		String base64Str = Base64Utils.encode(msg);
		return md5(base64Str, MAPPING_CHAR_PLUS);
	}

	/**
	 * 原生的 md5 加密
	 * @param msg
	 * @return
	 */
	public static String originMD5(String msg) {
		return md5(msg, MAPPING_CHAR_DEFAULT);
	}

	/**
	 * md5实现算法
	 * @param msg 加密消息
	 * @param mapperchars 匹配字符串
	 * @return
	 */
	private static String md5(String msg, char[] mapperchars) {
		assert msg != null;
		try {
			byte[] bytes = msg.getBytes(DEFAULT_CHARCODE);
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			bytes = md.digest();
			int j = bytes.length;
			char[] chars = new char[j * 2];
			int k = 0;
			for (int i = 0; i < bytes.length; i++) {
				byte b = bytes[i];
				chars[k++] = mapperchars[b >>> 4 & 0xf];
				chars[k++] = mapperchars[b & 0xf];
			}
			return new String(chars);
		} catch (Exception e) {
			return "";
		}
	}

}