package com.paleo.blog.service.sys.account.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.Cacheable;
import com.paleo.blog.model.sys.account.Account;
import com.paleo.blog.remote.sys.account.IAccountService;
import com.paleo.blog.service.sys.account.dao.IAccountDao;

@Service("blog.service.sys.account.imp.AccountService")
public class AccountService implements IAccountService{
	@Resource
	private IAccountDao accountDao;

	@Cacheable(cacheName=CacheNamespace.ACCOUNT)
	public Account getAccount(Long userId) {
		return accountDao.getAccount(userId);
	}

}
