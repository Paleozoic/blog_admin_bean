package com.paleo.blog.cache;
/**
 * 缓存命名空间（其实就是一个前缀）
 * redis的Hash  Set  前缀  db等都可作为【命名空间】，但是redis的过期是通过key维护的，那就只能使用db或者前缀了。
 * @author Paleo
 *
 */
public class CacheNamespace {
	/**
	 * 分隔符
	 */
	public final static String SPT_DOT = ".";
	public final static String SPT_ROD = "—";
	public final static String SPT_LEFT_MID = "[";
	public final static String SPT_RIGHT_MID = "]";
	/**
	 * 不存在的命名空间
	 */
	public final static String NOT_EXIST = "_NOT_EXIST_";
	/**
	 * 命名空间（key前缀）
	 */
	public final static String ACCOUNT = "_ACCOUNT_";
	public static final String DEPT = "_SYS_DEPT_";
	//menuService下的2个方法
	public static final String ROLE_MENU = "_ROLE_MENU_";
	public static final String USER_MENU = "_USER_MENU_";
	public static final String USER_ROLE = "_USER_ROLE_";
}
