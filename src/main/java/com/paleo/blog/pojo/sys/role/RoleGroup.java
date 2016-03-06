package com.paleo.blog.pojo.sys.role;

public class RoleGroup {
	private Long roleGroupId;
	private Long upperRoleGroupId;
	private String upperRoleGroupName;
	private String roleGroupName;
	private Integer level;
	private Integer orderNo;
	private String tree;
	private Integer status;
	
	public Long getRoleGroupId() {
		return roleGroupId;
	}
	public void setRoleGroupId(Long roleGroupId) {
		this.roleGroupId = roleGroupId;
	}
	public Long getUpperRoleGroupId() {
		return upperRoleGroupId;
	}
	public void setUpperRoleGroupId(Long upperRoleGroupId) {
		this.upperRoleGroupId = upperRoleGroupId;
	}
	public String getUpperRoleGroupName() {
		return upperRoleGroupName;
	}
	public void setUpperRoleGroupName(String upperRoleGroupName) {
		this.upperRoleGroupName = upperRoleGroupName;
	}
	public String getRoleGroupName() {
		return roleGroupName;
	}
	public void setRoleGroupName(String roleGroupName) {
		this.roleGroupName = roleGroupName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getTree() {
		return tree;
	}
	public void setTree(String tree) {
		this.tree = tree;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
