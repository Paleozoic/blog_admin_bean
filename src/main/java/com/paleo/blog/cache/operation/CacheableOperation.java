package com.paleo.blog.cache.operation;

import com.paleo.blog.cache.operation.basic.CacheOperation;

public class CacheableOperation extends CacheOperation {
	private int expire;

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}
	
	@Override
	protected StringBuilder getOperationDescription() {
		StringBuilder sb = super.getOperationDescription();
		sb.append(" | expire='");
		sb.append(this.expire);
		sb.append("'");
		return sb;
	}
}
