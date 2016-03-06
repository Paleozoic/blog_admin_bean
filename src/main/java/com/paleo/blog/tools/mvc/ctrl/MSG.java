package com.paleo.blog.tools.mvc.ctrl;

public enum MSG {
	ADD_SUCCESS("新增成功！"), UPDATE_SUCCESS("更新成功！"),DELETE_SUCCESS("删除成功！"),
	ADD_FAIL("新增失败！"), UPDATE_FAIL("更新失败！"),DELETE_FAIL("删除失败！"), 
	AUTHRIZE_SUCCESS("授权成功！"), AUTHRIZE_FAIL("授权失败！");
	
	private String msg;

	private MSG(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
