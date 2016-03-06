package com.paleo.blog.pojo.sys.dept;

public class Dept {
	private Long deptId;
	private Long upperDeptId;
	private String upperDeptName;
	private String deptName;
	private Integer level;
	private Integer orderNo;
	private String tree;
	private Integer status;
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getUpperDeptId() {
		return upperDeptId;
	}
	public void setUpperDeptId(Long upperDeptId) {
		this.upperDeptId = upperDeptId;
	}
	public String getUpperDeptName() {
		return upperDeptName;
	}
	public void setUpperDeptName(String upperDeptName) {
		this.upperDeptName = upperDeptName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
