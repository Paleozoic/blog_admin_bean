package com.paleo.blog.service.sys.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.paleo.blog.pojo.sys.dept.Dept;
import com.paleo.blog.pojo.sys.user.DeptUsers;
import com.paleo.blog.pojo.sys.user.User;
import com.paleo.blog.pojo.sys.user.UserRoles;

public interface IUserDao {

	public List<User> showUserPage(@Param("bo")DeptUsers deptUsers, RowBounds bounds);

	public void addUser(@Param("bo")User user);

	public User getUserById(@Param("userId")Long userId);

	public void updateUser(@Param("bo")User user);

	public List<User> isUnique(@Param("bo")User user);

	public void authorizeUser(@Param("bo")UserRoles userRoles);

	public void deleteUserRolesByUserId(@Param("userId")Long userId);

	public void addUserRoles(@Param("bo")UserRoles userRoles);

	public void deleteUserById(@Param("userId")Long userId);

	public List<User> getUserListInDept(@Param("bo")Dept dept);

	public User getUserByName(@Param("userName")String userName);

}
