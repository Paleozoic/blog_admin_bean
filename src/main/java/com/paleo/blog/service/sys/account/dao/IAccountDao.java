package com.paleo.blog.service.sys.account.dao;

import org.apache.ibatis.annotations.Param;

import com.paleo.blog.model.sys.account.Account;

public interface IAccountDao {

	Account getAccount(@Param("userId")Long userId);
	
}
