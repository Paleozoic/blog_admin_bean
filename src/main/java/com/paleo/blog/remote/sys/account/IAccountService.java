package com.paleo.blog.remote.sys.account;

import com.paleo.blog.model.sys.account.Account;

public interface IAccountService {

	Account getAccount(Long userId);

}
