/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paleo.blog.cache.operation.basic;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.util.Assert;

public abstract class CacheOperation implements BasicOperation {

	private String name = "";

	private Set<String> cacheNames = Collections.emptySet();

	private String key = "";

	private String beforeCondition = "";
	
	private String afterCondition = "";

	private String afterCache = "";
	
	public void setName(String name) {
		Assert.hasText(name);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCacheName(String cacheName) {
		Assert.hasText(cacheName);
		this.cacheNames = Collections.singleton(cacheName);
	}

	public void setCacheNames(String... cacheNames) {
		this.cacheNames = new LinkedHashSet<String>(cacheNames.length);
		for (String cacheName : cacheNames) {
			Assert.hasText(cacheName, "Cache name must be non-null if specified");
			this.cacheNames.add(cacheName);
		}
	}

	@Override
	public Set<String> getCacheNames() {
		return this.cacheNames;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}




	@Override
	public boolean equals(Object other) {
		return (other instanceof CacheOperation && toString().equals(other.toString()));
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return getOperationDescription().toString();
	}

	protected StringBuilder getOperationDescription() {
		StringBuilder result = new StringBuilder(getClass().getSimpleName());
		result.append("[").append(this.name);
		result.append("] caches=").append(this.cacheNames);
		result.append(" | key='").append(this.key);
		result.append("' | beforeCondition='").append(this.beforeCondition);
		result.append("' | afterCondition='").append(this.afterCondition);
		result.append("' | afterCache='").append(this.afterCache).append("'");
		return result;
	}
	
	public String getBeforeCondition() {
		return beforeCondition;
	}

	public void setBeforeCondition(String beforeCondition) {
		this.beforeCondition = beforeCondition;
	}

	public String getAfterCondition() {
		return afterCondition;
	}

	public void setAfterCondition(String afterCondition) {
		this.afterCondition = afterCondition;
	}

	public String getAfterCache() {
		return afterCache;
	}

	public void setAfterCache(String afterCache) {
		this.afterCache = afterCache;
	}

	public void setCacheNames(Set<String> cacheNames) {
		this.cacheNames = cacheNames;
	}

}
