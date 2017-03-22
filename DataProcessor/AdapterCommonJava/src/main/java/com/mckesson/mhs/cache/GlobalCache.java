package com.mckesson.mhs.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalCache {
	
	private static final Map<String, Map<String, String>> cache = new ConcurrentHashMap<>();
	
	private static Map<String, String> getAll(String domain) {
		return cache.get(domain);
	}
	/**
	 * Default constructor
	 */
	public GlobalCache(){
		
	}
	
	/**
	 * This method is used to get the values from cache by using domain name and key
	 * @param domain
	 * @param key
	 * @return
	 */
	public static String get(String domain, String key) {
		Map<String, String> domainMap = getAll(domain);		
		return domainMap != null ? domainMap.get(key) : null;
	}
	
	/**
	 * This method is used to put the values into cache to a particular domain
	 * @param domain
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Map<String, String> putAll(String domain, String key, String value) {
		Map<String, String> domainMap = getAll(domain);
		if(domainMap == null) {
			domainMap = new HashMap<String, String>();
		}
		domainMap.put(key, value);
		return cache.put(domain, domainMap);
	}
	
	/**
	 * This method is used to clear the cache
	 * @param domain
	 * @return
	 */
	public static boolean remove(String domain) {
		cache.remove(domain);
		return true;
	}

}
