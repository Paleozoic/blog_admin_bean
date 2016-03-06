package com.paleo.blog.model.core.bo;

import java.util.List;

public class ShiroMenus {
	private List<Menu> hasMenuCode;
	private List<Menu> noMenuCode;
	
	public List<Menu> getHasMenuCode() {
		return hasMenuCode;
	}
	public void setHasMenuCode(List<Menu> hasMenuCode) {
		this.hasMenuCode = hasMenuCode;
	}
	public List<Menu> getNoMenuCode() {
		return noMenuCode;
	}
	public void setNoMenuCode(List<Menu> noMenuCode) {
		this.noMenuCode = noMenuCode;
	}
}
