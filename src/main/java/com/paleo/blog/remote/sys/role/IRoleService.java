package com.paleo.blog.remote.sys.role;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.pojo.sys.menu.Menu;
import com.paleo.blog.pojo.sys.role.Role;
import com.paleo.blog.pojo.sys.role.RoleGroupRoles;
import com.paleo.blog.pojo.sys.role_group.RoleGroup;

public interface IRoleService {

	/**
	 * 根据用户，获取其下角色列表
	 * @param user
	 * @return
	 */
	List<Role> getRoleList(Long userId);

	/**
	 * 根据用户，获取其下角色列表（分页）
	 * @param bo
	 * @param bounds
	 * @return
	 */
	PageInfo<Role> showRolePage(RoleGroupRoles bo, RowBounds bounds);

	/**
	 * 新增角色
	 * @param role
	 */
	void addRole(Role role);
	/**
	 * 用户已授权的角色
	 * @param userId
	 * @return
	 */
	List<Role> getUserRoleList(Long userId);
	/**
	 * 获取角色
	 * @param roleId
	 * @return
	 */
	Role getRoleById(Long roleId);
	/**
	 * 更新角色
	 * @param wrapBean
	 */
	void updateRole(Role role);
	/**
	 * 删除角色（逻辑删除）
	 * @param roleId
	 */
	void deleteRoleById(Long roleId);
	/**
	 * 角色授权菜单
	 * @param role 
	 * @param menuList
	 */
	void authorizeRole(Role role, List<Menu> menuList);
	/**
	 * 获取角色分组下的角色
	 * @param roleGroup
	 * @return
	 */
	List<Role> getRoleListInGroup(RoleGroup bo);

}
