package com.paleo.blog.model.sys.menu.tree.ztree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 封装给原生的ztree使用
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Menu {
	private Long menuId;
	private String menuCode;
	private Long upperMenuId;
	private String menuName;
	private Integer type;
	@JsonProperty("menuLevel")
	private Integer level;
	private Integer orderNo;
	@JsonProperty("menuUrl")
	private String url;
	private String tree;
	private Integer status;
	
	@JsonIgnore
	public Menu getMenu(){
		Menu menu = new Menu();
		menu.setMenuId(menuId);
		menu.setMenuCode(menuCode);
		menu.setUpperMenuId(upperMenuId);
		menu.setMenuName(menuName);
		menu.setType(type);
		menu.setLevel(level);
		menu.setOrderNo(orderNo);
		menu.setUrl(url);
		menu.setTree(tree);
		return menu;
//		return this;
		//返回的是子类，包含了children,return this当前对象的引用
		//猜测原理类似：super.getClass();返回运行时类型。http://bbs.csdn.net/topics/370010127 2楼
	}
	
	public void setMenu(Menu menu){
		this.menuId = menu.getMenuId();
		this.upperMenuId = menu.getUpperMenuId();
		this.menuName = menu.getMenuName();
		this.type = menu.getType();
		this.level = menu.getLevel();
		this.orderNo = menu.getOrderNo();
		this.url = menu.getUrl();
		this.tree = menu.getTree();
		this.status = menu.getStatus();
	}
	
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getUpperMenuId() {
		return upperMenuId;
	}

	public void setUpperMenuId(Long upperMenuId) {
		this.upperMenuId = upperMenuId;
	}

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
