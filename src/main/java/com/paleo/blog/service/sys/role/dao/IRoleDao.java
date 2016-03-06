package com.paleo.blog.service.sys.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.paleo.blog.pojo.sys.menu.Menu;
import com.paleo.blog.pojo.sys.role.Role;
import com.paleo.blog.pojo.sys.role.RoleGroupRoles;
import com.paleo.blog.pojo.sys.role_group.RoleGroup;


public interface IRoleDao {

	List<Role> getRoleList(@Param("userId")Long userId);

	List<Role> showRolePage(@Param("bo")RoleGroupRoles bo, RowBounds bounds);

	void addRole(@Param("bo")Role role);

	List<Role> getUserRoleList(@Param("userId")Long userId);

	Role getRoleById(@Param("roleId")Long roleId);

	void updateRole(@Param("bo")Role role);

	void deleteRoleById(@Param("roleId")Long roleId);

	void authorizeRole(@Param("menuList")List<Role> menuList);

	void deleteRoleMenusById(@Param("roleId")Long roleId);

	void addRoleMenus(@Param("role")Role role, @Param("menuList")List<Menu> menuList);

	void deleteUserRolesByRoleId(@Param("roleId")Long roleId);

	List<Role> getRoleListInGroup(@Param("bo")RoleGroup roleGroup);

}
