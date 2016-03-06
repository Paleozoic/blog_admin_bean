package com.paleo.blog.model.sys.role.bo;

public class SelectedRole {
	private Long roleId;
	private String roleName;
	private Long roleGroupId;
	private String roleGroupName;
	private Integer status;
	private boolean selected;
	private Integer isManaged;
	
	public SelectedRole(com.paleo.blog.pojo.sys.role.Role role) {
		this.roleId = role.getRoleId() ;
		this.roleName = role.getRoleName() ;
		this.roleGroupId = role.getRoleGroupId() ;
		this.roleGroupName = role.getRoleGroupName() ;
		this.status = role.getStatus() ;
		this.selected = false ;
	}
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
	public Long getRoleGroupId() {
		return roleGroupId;
	}
	public void setRoleGroupId(Long roleGroupId) {
		this.roleGroupId = roleGroupId;
	}
	public String getRoleGroupName() {
		return roleGroupName;
	}
	public void setRoleGroupName(String roleGroupName) {
		this.roleGroupName = roleGroupName;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Integer getIsManaged() {
		return isManaged;
	}
	public void setIsManaged(Integer isManaged) {
		this.isManaged = isManaged;
	}
}
