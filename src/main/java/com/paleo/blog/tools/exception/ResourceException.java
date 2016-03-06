package com.paleo.blog.tools.exception;

/**
 * 资源异常： 短信或者文件错误所抛出的异常
 * @author hc
 */
public class ResourceException  extends RuntimeException {

	private static final long serialVersionUID = -5241924709578991055L;

	public ResourceException(Exception e) {
		super(e);
	}
	
	public ResourceException(String message) {
		super(message);
	}
	
	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}
	
}