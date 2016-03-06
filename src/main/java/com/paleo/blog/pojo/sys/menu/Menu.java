package com.paleo.blog.pojo.sys.menu;

public class Menu {
	private Long menuId;
	private String menuCode;
	private Long upperMenuId;
	private String upperMenuName;
	private String menuName;
	private Integer type;
	private Integer level;
	private Integer orderNo;
	private String url;
	private String tree;
	private Integer status;
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUpperMenuName() {
		return upperMenuName;
	}
	public void setUpperMenuName(String upperMenuName) {
		this.upperMenuName = upperMenuName;
	}
	public Long getUpperMenuId() {
		return upperMenuId;
	}
	public void setUpperMenuId(Long upperMenuId) {
		this.upperMenuId = upperMenuId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTree() {
		return tree;
	}
	public void setTree(String tree) {
		this.tree = tree;
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
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
}
