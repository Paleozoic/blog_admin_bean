package com.paleo.blog.tools.jedis;

public enum RespCode {
	OK("OK");
	
	private String resp;
	
	private RespCode(String resp){
		this.resp = resp;
	}
	
	public String getResp(){
		return this.resp;
	}
}
