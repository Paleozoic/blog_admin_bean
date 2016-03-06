package com.paleo.blog.tools.encrypt;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;



public class Base64Utils {
	
	private final static String DEFAULT_CHARCODE = "utf-8";

	/**
	 * 编码
	 * @param msg
	 * @return
	 */
	public static String encode(String msg){
		try {
			byte[] bytes;
			bytes = msg.getBytes(DEFAULT_CHARCODE);
			Base64 base64=new Base64();
			base64.encode(bytes);
			return  new String(bytes, DEFAULT_CHARCODE);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	/**
	 * 解吗
	 * @param msg
	 * @return
	 */
	public static String decode(String msg){
		try {
			byte[] bytes;
			bytes = msg.getBytes(DEFAULT_CHARCODE);
			Base64 base64=new Base64();
			base64.decode(bytes);
			return new String(bytes, DEFAULT_CHARCODE);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

}