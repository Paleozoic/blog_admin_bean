package com.paleo.blog.tools.string;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则 http://www.cnblogs.com/yirlin/archive/2006/04/12/373222.html
 * @author mzz
 *
 */
public class PatternUtils {

	private final static Map<String, String> REGULAR_PATTERN_MAP = new HashMap<String, String>();

	static {
		REGULAR_PATTERN_MAP.put("url", "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		// 数字
		REGULAR_PATTERN_MAP.put("Numeric", "^[0-9]*$");
		REGULAR_PATTERN_MAP.put("PositiveNumber", "^[1-9]\\d*$");
		// 手机
		REGULAR_PATTERN_MAP.put("Cellphone", "^((1[3][0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		// 邮箱
		REGULAR_PATTERN_MAP.put("Email", "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		// 用户名 （6-20位包含字母数字 ‘.’ <点> ‘_’ <下划线>）
		REGULAR_PATTERN_MAP.put("usrName", "^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){5,19}$");
		// 身份证（包含一代15位，二代18位）
		REGULAR_PATTERN_MAP.put("Card15", "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		REGULAR_PATTERN_MAP.put("Card18", "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
	}

	private static boolean isPatternFromMap(String mapKey, String str) {
		String pattern = REGULAR_PATTERN_MAP.get(mapKey);
		return isPattern(pattern, str);
	}

	/**
	 * 自定义规则进行匹配
	 * @param patern 匹配表达式
	 * @param str  需要验证的字符串
	 * @return
	 */
	public static boolean isPattern(String patern, String str) {
		if (StringUtils.isNotEmpty(str)) {
			Pattern pattern = Pattern.compile(patern);
			return pattern.matcher(str).matches();
		}
		return false;
	}

	public static boolean isUrl(String url) {
		return isPatternFromMap("url", url);
	}

	/**
	 * 是否为数字格式
	 * @param str 需要验证的字符串
	 * @return
	 */
	public static boolean isNumeric(String str) {
		return isPatternFromMap("Numeric", str);
	}

	/**
	 *  是否为正整数 
	 * @param str 需要验证的字符串
	 * @return
	 */
	public static boolean isPositiveNumber(String str) {
		return isPatternFromMap("PositiveNumber", str);
	}

	/**
	 * 是否是手机号
	 * @param str 需要验证的字符串
	 */
	public static boolean isCellphone(String str) {
		return isPatternFromMap("Cellphone", str);
	}

	/**
	 * 是否是邮箱
	 * @param str 需要验证的字符串
	 */
	public static boolean isEmail(String str) {
		return isPatternFromMap("Email", str);
	}

	/**
	 * 是否是用户名
	 * @param str 需要验证的字符串
	 */
	public static boolean isUsrName(String str) {
		return isPatternFromMap("usrName", str);
	}

	/**
	 * 是否是一代身份证
	 * @param str 需要验证的字符串
	 */
	public static boolean isCard15(String str) {
		return isPatternFromMap("Card15", str);
	}

	/**
	 * 是否是二代身份证
	 * @param str 需要验证的字符串
	 */
	public static boolean isCard18(String str) {
		return isPatternFromMap("Card18", str);
	}

	/**
	 * 验证一代和二代身份证【是否是身份证】
	 * @param str 需要验证的字符串
	 */
	public static boolean isCard(String str) {
		return (isCard15(str) || isCard18(str));
	}

}