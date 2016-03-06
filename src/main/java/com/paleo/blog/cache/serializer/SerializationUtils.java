/*
 * Copyright 2011-2013 the original author or authors.
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
package com.paleo.blog.cache.serializer;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
/**
 * Utility class with various serialization-related methods.
 * 
 * @author Costin Leau
 */
public abstract class SerializationUtils {
 
    protected static final String EMPTY_STRING = "";
 
    @SuppressWarnings("unchecked")
    static <T extends Collection<?>> T deserializeValues(Collection<String> rawValues, Class<T> type,
            RedisSerializer<?> redisSerializer) {
        // connection in pipeline/multi mode
        if (rawValues == null) {
            return null;
        }
 
        Collection<Object> values = (List.class.isAssignableFrom(type) ? new ArrayList<Object>(rawValues.size())
                : new LinkedHashSet<Object>(rawValues.size()));
        for (String bs : rawValues) {
            values.add(redisSerializer.deserialize(bs));
        }
 
        return (T) values;
    }
 
    @SuppressWarnings("unchecked")
    public static <T> Set<T> deserialize(Set<String> rawValues, RedisSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, Set.class, redisSerializer);
    }
 
    @SuppressWarnings("unchecked")
    public static <T> List<T> deserialize(List<String> rawValues, RedisSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, List.class, redisSerializer);
    }
 
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> deserialize(Collection<String> rawValues, RedisSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, List.class, redisSerializer);
    }
 
    public static <T> Map<T, T> deserialize(Map<String, String> rawValues, RedisSerializer<T> redisSerializer) {
        if (rawValues == null) {
            return null;
        }
        Map<T, T> ret = new LinkedHashMap<T, T>(rawValues.size());
        for (Map.Entry<String, String> entry : rawValues.entrySet()) {
            ret.put(redisSerializer.deserialize(entry.getKey()), redisSerializer.deserialize(entry.getValue()));
        }
        return ret;
    }
 
    @SuppressWarnings("unchecked")
    public static <HK, HV> Map<HK, HV> deserialize(Map<String, String> rawValues, RedisSerializer<HK> hashKeySerializer,
            RedisSerializer<HV> hashValueSerializer) {
        if (rawValues == null) {
            return null;
        }
        Map<HK, HV> map = new LinkedHashMap<HK, HV>(rawValues.size());
        for (Map.Entry<String, String> entry : rawValues.entrySet()) {
            // May want to deserialize only key or value
            HK key = hashKeySerializer != null ? (HK) hashKeySerializer.deserialize(entry.getKey()) : (HK) entry.getKey();
            HV value = hashValueSerializer != null ? (HV) hashValueSerializer.deserialize(entry.getValue()) : (HV) entry
                    .getValue();
            map.put(key, value);
        }
        return map;
    }

    public static boolean isEmpty(String str) {
		return (str==null||str.length()==0);
	}
}