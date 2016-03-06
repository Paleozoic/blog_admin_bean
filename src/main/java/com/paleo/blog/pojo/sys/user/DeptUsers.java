package com.paleo.blog.pojo.sys.user;

import com.paleo.blog.pojo.sys.dept.Dept;

public class DeptUsers {
	private Dept dept;
	private User user;
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
