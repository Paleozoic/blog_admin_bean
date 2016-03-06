package com.paleo.blog.cache.operation;

import com.paleo.blog.cache.operation.basic.CacheOperation;

public class CacheEvictOperation extends CacheOperation {
	private boolean allEntries;
	private boolean patternMode;
	
	public void setAllEntries(boolean allEntries) {
		this.allEntries = allEntries;
	}
	public boolean isPatternMode() {
		return patternMode;
	}
	public void setPatternMode(boolean patternMode) {
		this.patternMode = patternMode;
	}
	public boolean isAllEntries() {
		return allEntries;
	}
	
	@Override
	protected StringBuilder getOperationDescription() {
		StringBuilder sb = super.getOperationDescription();
		sb.append(" | allEntries='");
		sb.append(this.allEntries);
		sb.append("'");
		sb.append(" | patternMode='");
		sb.append(this.patternMode);
		sb.append("'");
		return sb;
	}
}
