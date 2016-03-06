package com.paleo.test.sys.shiro;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class shiro  {

	
	@Test
	public void test(){
		Pattern p = Pattern.compile("^perms\\[(.*)\\]$");  
		Matcher match = p.matcher("perms[sys:user:add]");  
		if(match.find()){  
			for( int i = match.start();i<=match.end();i++)
				System.out.println(match.group(i));  
		}
	}
	
	
//	@Test
//	public void test(){
//		Pattern p = Pattern.compile("CN=(\\S*?),");
//	    
//	    Matcher match = p.matcher("CN=511027XXX01027325X, OU=UCIT, O=Soft");
//	    int i=0;
//	    while (match.find()){
//	    	System.out.println(i);
//			System.out.println(match.group(i));  
//			i++;
//	    }
//	}
	
}
