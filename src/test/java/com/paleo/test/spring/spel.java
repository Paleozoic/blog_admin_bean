package com.paleo.test.spring;

import org.junit.Test;

import com.paleo.blog.cache.tools.spel.SpELUtils;
import com.paleo.test.basic.BasicTest;

public class spel extends BasicTest{
	
	
	@Test
	public void test(){
		String afterCache="new com.paleo.blog.shiro.updatePermission().update()";
		SpELUtils.getElValue(afterCache, null, "", Void.class);
	}
}
