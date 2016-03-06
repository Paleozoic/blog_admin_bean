package com.paleo.test.sys.login;

import org.junit.Test;

import com.paleo.blog.tools.encrypt.Base64Utils;
import com.paleo.blog.tools.encrypt.MD5Utils;
import com.paleo.test.basic.BasicTest;

public class login extends BasicTest{

	
	@Test
	public void test(){
//		System.out.println(Base64Utils.encode("123"));//[B@3f4f9acd
		System.out.println(MD5Utils.customMD5("123"));//18e69e58d281dadacbc62ea3a30374f4
	}
}
