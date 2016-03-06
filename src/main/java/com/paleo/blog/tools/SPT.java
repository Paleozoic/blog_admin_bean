package com.paleo.blog.tools;

/**
 * separator 分隔符
 * @author Paleo
 *
 */
public enum SPT {
	DOT("."),ROD("-"),COLON(":");
	
	private String spt;

	private SPT(String spt) {
		this.spt = spt;
	}

	public String getSpt() {
		return spt;
	}
}
