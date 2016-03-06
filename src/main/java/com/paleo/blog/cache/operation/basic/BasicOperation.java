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

import java.util.Set;

/**
 * The base interface that all cache operations must implement.
 *
 * @author Stephane Nicoll
 * @since 4.1
 */
public interface BasicOperation {

	/**
	 * Return the cache name(s) associated with the operation.
	 * TODO 事实上这里我设计成了一个注解的cacheName只有一个，所以此方法其实是可以省略的
	 */
	Set<String> getCacheNames();

}
