package com.paleo.blog.remote.sys.user;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.pojo.sys.dept.Dept;
import com.paleo.blog.pojo.sys.user.DeptUsers;
import com.paleo.blog.pojo.sys.user.User;
import com.paleo.blog.pojo.sys.user.UserRoles;

public interface IUserService {

	/**
	 * 用户分页
	 * @param deptUser
	 * @param bounds
	 * @return
	 */
	PageInfo<User> showUserPage(DeptUsers deptUsers, RowBounds bounds);
	/**
	 * 新增用户
	 * @param user
	 */
	void addUser(User user);
	/**
	 * 根据ID获取用户
	 * @param userId
	 * @return
	 */
	User getUserById(Long userId);
	/**
	 * 更新用户
	 * @param user
	 */
	void updateUser(User user);
	/**
	 * 判断用户是否是唯一存在
	 * @param user
	 * @return
	 */
	List<User> isUnique(User user);
	/**
	 * 授权用户
	 * @param userRoles 一对多的用户角色
	 */
	void authorizeUser(UserRoles userRoles);
	/**
	 * 删除用户（逻辑删除）
	 * @param userId
	 */
	void deleteUserById(Long userId);
	/**
	 * 获取dept下的用户（不包含dept的子部门用户）
	 * @param dept
	 * @return
	 */
	List<User> getUserListInDept(Dept dept);
	/**
	 * 获取用户 by Name
	 * @param userName
	 * @return
	 */
	User getUserByName(String userName);

}
