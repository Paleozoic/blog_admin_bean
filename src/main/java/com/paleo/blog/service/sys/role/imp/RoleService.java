package com.paleo.blog.service.sys.role.imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.cache.annotation.Cacheable;
import com.paleo.blog.cache.annotation.Caching;
import com.paleo.blog.pojo.sys.menu.Menu;
import com.paleo.blog.pojo.sys.role.Role;
import com.paleo.blog.pojo.sys.role.RoleGroupRoles;
import com.paleo.blog.pojo.sys.role_group.RoleGroup;
import com.paleo.blog.remote.sys.role.IRoleService;
import com.paleo.blog.service.sys.role.dao.IRoleDao;

@Service("blog.service.sys.role.imp.RoleService")
public class RoleService implements IRoleService{

	@Resource
	private IRoleDao roleDao;
	
	public List<Role> getRoleList(Long userId) {
		return roleDao.getRoleList(userId);
	}

	public PageInfo<Role> showRolePage(RoleGroupRoles bo, RowBounds bounds) {
		return new PageInfo<Role>(roleDao.showRolePage(bo,bounds));
	}

	@Transactional
	public void addRole(Role role) {
		roleDao.addRole(role);
	}

	@Cacheable(cacheName=CacheNamespace.USER_ROLE)
	public List<Role> getUserRoleList(Long userId) {
		return roleDao.getUserRoleList(userId);
	}

	public Role getRoleById(Long roleId) {
		return roleDao.getRoleById(roleId);
	}
	
	@Caching(evict=
			 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
			  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
			)
	@Transactional
	public void updateRole(Role role) {
		roleDao.updateRole(role);
	}

	@Caching(evict=
		 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ROLE_MENU,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void deleteRoleById(Long roleId) {
		roleDao.deleteRoleById(roleId);
		roleDao.deleteRoleMenusById(roleId);
		roleDao.deleteUserRolesByRoleId(roleId);
	}
	
	@Caching(evict=
		  {@CacheEvict(cacheName=CacheNamespace.ROLE_MENU,allEntries=true),
		   @CacheEvict(cacheName=CacheNamespace.USER_MENU,allEntries=true),
		   @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void authorizeRole(Role role,List<Menu> menuList) {
		//1. 删除原角色菜单
		roleDao.deleteRoleMenusById(role.getRoleId());
		//2. 增加现角色菜单
		if(menuList!=null&&!menuList.isEmpty()){
			roleDao.addRoleMenus(role,menuList);
		}
	}

	public List<Role> getRoleListInGroup(RoleGroup roleGroup) {
		return roleDao.getRoleListInGroup(roleGroup);
	}

}
