package com.paleo.test.tools;

import org.junit.Test;

import com.paleo.blog.tools.encrypt.MD5Utils;

public class md5 {
	@Test
	public void test(){
//		System.out.println(md5("123", MAPPING_CHAR_PLUS));
		System.out.println(MD5Utils.customMD5("123"));//8f3071b062012ea11095f821149a7558
	}
}
