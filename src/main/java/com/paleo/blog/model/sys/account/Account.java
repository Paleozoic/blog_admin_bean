package com.paleo.blog.model.sys.account;

import java.io.Serializable;
import java.util.List;

import com.paleo.blog.model.sys.account.bo.Dept;
import com.paleo.blog.model.sys.account.bo.Menu;
import com.paleo.blog.model.sys.account.bo.Role;
import com.paleo.blog.model.sys.account.bo.User;

public class Account implements Serializable{
	 
	private static final long serialVersionUID = 1199771145897548196L;
	
	private User user;
	private Dept dept;
	private List<Menu> menuList;
	private List<Role> roleList;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}
