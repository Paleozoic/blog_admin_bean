package com.paleo.test.sys.account;

import javax.annotation.Resource;

import org.junit.Test;

import com.paleo.blog.model.sys.account.Account;
import com.paleo.blog.remote.sys.account.IAccountService;
import com.paleo.test.basic.BasicTest;

public class account extends BasicTest{

	@Resource(name="blog.service.sys.account.imp.AccountService")
	IAccountService accountService;
	
	@Test
	public void test(){
		Account account = accountService.getAccount(3l);
		System.out.println(account);//18e69e58d281dadacbc62ea3a30374f4
	}
}
