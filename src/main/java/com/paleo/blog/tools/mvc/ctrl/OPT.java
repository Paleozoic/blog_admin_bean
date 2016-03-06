package com.paleo.blog.tools.mvc.ctrl;

public enum OPT {
	LIST("list"), ADD("add"), UPDATE("update"), CANCEL("cancel"), DELETE("delete"), UPLOAD("upload"), 
	DOWNLOAD("download"), EXPORT("export"),AJAX("ajax"), AUTHORIZE("authorize"), LOGIN("login");
	
	private String opt;

	private OPT(String opt) {
		this.opt = opt;
	}

	public String getOpt() {
		return opt;
	}
}
