package com.paleo.blog.service.sys.user.imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.cache.annotation.Caching;
import com.paleo.blog.pojo.sys.dept.Dept;
import com.paleo.blog.pojo.sys.user.DeptUsers;
import com.paleo.blog.pojo.sys.user.User;
import com.paleo.blog.pojo.sys.user.UserRoles;
import com.paleo.blog.remote.sys.user.IUserService;
import com.paleo.blog.service.sys.user.dao.IUserDao;

@Service("blog.service.sys.user.imp.UserService")
public class UserService implements IUserService{

	@Resource
	private IUserDao userDao;
	
	@Override
	public PageInfo<User> showUserPage(DeptUsers deptUsers, RowBounds bounds) {
		return new PageInfo<User>(userDao.showUserPage(deptUsers,bounds));
	}

	@Transactional
	public void addUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public User getUserById(Long userId) {
		return userDao.getUserById(userId);
	}

	@Caching(evict=
		 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.USER_MENU,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public List<User> isUnique(User user) {
		return userDao.isUnique(user);
	}

	@Caching(evict=
		 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void authorizeUser(UserRoles userRoles) {
		//1. 删除原用户角色
		userDao.deleteUserRolesByUserId(userRoles.getUser().getUserId());
		//2. 增加现用户角色
		if(userRoles.getRoleList()!=null&&!userRoles.getRoleList().isEmpty()){
			userDao.addUserRoles(userRoles);
		}
	}

	@Caching(evict=
		 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.USER_MENU,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void deleteUserById(Long userId) {
		userDao.deleteUserById(userId);
		userDao.deleteUserRolesByUserId(userId);
	}

	@Override
	public List<User> getUserListInDept(Dept dept) {
		return userDao.getUserListInDept(dept);
	}

	@Override
	public User getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}

}
