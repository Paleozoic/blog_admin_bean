package com.paleo.blog.model.core.bo;

import java.util.List;

import com.paleo.blog.tools.reflect.WrapUtils;

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
	private String perms;//权限
	
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
	public String getPerms() {
		return perms;
	}
	public void setPerms(String perms) {
		this.perms = perms;
	}
	/**
	 * 转为com.paleo.blog.pojo.sys.menu.Menu，就是去除perms字段
	 * @param hasMenuCode
	 * @return
	 */
	public static List<com.paleo.blog.pojo.sys.menu.Menu> toMenu(List<com.paleo.blog.model.core.bo.Menu> hasMenuCode) {
		List<com.paleo.blog.pojo.sys.menu.Menu> list = WrapUtils.list();
		for(com.paleo.blog.model.core.bo.Menu e:hasMenuCode){
			com.paleo.blog.pojo.sys.menu.Menu menu = new com.paleo.blog.pojo.sys.menu.Menu();
			menu.setLevel(e.getLevel());
			menu.setMenuCode(e.getMenuCode());
			menu.setMenuId(e.getMenuId());
			menu.setMenuName(e.getMenuName());
			menu.setOrderNo(e.getOrderNo());
			menu.setStatus(e.getStatus());
			menu.setTree(e.getTree());
			menu.setType(e.getType());
			menu.setUpperMenuId(e.getUpperMenuId());
			menu.setUpperMenuName(e.getUpperMenuName());
			menu.setUrl(e.getUrl());
			list.add(menu);
		}
		return list;
	}
}
