package com.paleo.blog.pojo.sys.user;

public class Role {
	private Long roleId;
	private String roleName;
	private Integer status;
	//授权时该角色是否被管理
	private Integer isManaged;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsManaged() {
		return isManaged;
	}
	public void setIsManaged(Integer isManaged) {
		this.isManaged = isManaged;
	}
}
