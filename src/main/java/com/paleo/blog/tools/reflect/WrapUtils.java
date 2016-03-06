package com.paleo.blog.tools.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * <p>改变：根据公司的工具类修改，优化如下：</p>
 * <p>公司的实现类似于这个（但是没加缓存淘汰和线程安全）：http://blog.chinaunix.net/uid-25316333-id-3056068.html</p>
 * <p>原版对于get方法的判断是基于get方法的名字，然后截取字符串获取Bean的变量名</p>
 * <p>优化为通过getReadMethod获取，并将变量名存入HashMap，然后在构成缓存</p>
 * <p>结果：（1）对于符合JavaBean的一些属性名和方法名规范，如URL与getURL等，可以准确获取name和method</p>
 * <p>（2）效率提升，简单测试，速度提升一倍</p>
 * <p>原版缺少Map转Bean的方法，这里增加Map转Bean的处理</p>
 * <p>PS:属性如果Name，这种不合常理的命名，效率会特别慢。可能JDK内部对于name和Name的命名处理方式是不同的。所以命名一定要规范啊！</p>
 * <p>但是至于缓存是否淘汰，可以根据需求加入淘汰算法。</p>
 */
/**
 * 封装 ~ Bean的封装映射工具类
 * 
 * @author hc
 * @author Paleo 修改
 */
public class WrapUtils {

	//--------------------Bean to Map start---------------------------------
	/**
	 * 将对象bean转换为Map（增加异常判断）
	 * @param source
	 * @param source
	 * @return
	 */
	public static Map wrapBean(Object source) {
		if (source.getClass().isArray() || ReflectUtils.isCollection(source)) {
			throw new IllegalArgumentException("Bean不能是集合也不能是数组");
		} else if (ReflectUtils.isBasic(source.getClass())) {
			throw new IllegalArgumentException("Bean为基本数据类型，无需封装");
		} else {
			return PrivateWrapBean(source);
		}
	}
	
	/**
	 * 将数组转为ArrayList，ArrayList存的bean转为map
	 * @param arrays
	 * @return
	 */
	public static List wrapArray(Object arrays) {
		if (!arrays.getClass().isArray()) {
			throw new IllegalArgumentException("Bean必须是数组类型");
		}
		List list = list();
		int size = Array.getLength(arrays);
		for (int index = 0; index < size; index++) {
			Object obj = Array.get(arrays, index);
			Object wrapObj = toWrap(obj);
			list.add(wrapObj);
		}
		return list;
	}

	/**
	 * 将集合Collection转为ArrayList，ArrayList存的bean转为map
	 * @param arrays
	 * @return
	 */
	public static List wrapList(Collection cols) {
		List list = list();
		Iterator ite = cols.iterator();
		while (ite.hasNext()) {
			Object obj = ite.next();
			Object wrapObj = toWrap(obj);
			list.add(wrapObj);
		}
		return list;
	}
	
	/**
	 * 将对象bean转换为Map
	 * @param source bean
	 * @return
	 */
	private static Map PrivateWrapBean(Object source) {
		Class clz = source.getClass();
		Map mapVal = model();
		Map<String, Method> mtds = ReflectUtils.getAllGetMethodByCache(clz);
		Iterator iter = mtds.entrySet().iterator();
		while(iter.hasNext()){//遍历Map
			Object val = null;
			Map.Entry entry = (Map.Entry) iter.next(); 
			String name = (String) entry.getKey();
			Method mtd = (Method) entry.getValue();
			try {
				val = ReflectUtils.getValueByMethod(source, mtd);
			} catch (Exception cause) {
				throw new IllegalArgumentException("注入方法出错[" + clz + "]", cause);
			}
			mapVal.put(name, toWrap(val));
		}
		return mapVal;
	}
	/**
	 * 根据obj类型的不同返回不同的结果，是bean则返回Map
	 * @param obj
	 * @return
	 */
	private static Object toWrap(Object obj) {
		if (obj == null) {//null返回null
			return null;
		}
		Class clz = obj.getClass();
		if (ReflectUtils.isBasic(clz)) {//基础数据类型、void、Integer等，返回本身
			return obj;
		} else if (clz.isArray()) {//如果是数组
			return wrapArray(obj);
		} else if (ReflectUtils.isCollection(obj)) {//如果是集合
			return wrapList((Collection) obj);
		} else {
			//获取包名
			String packName = clz.getPackage().getName();
			// 基本包里面的数据直接返回
			if (packName.startsWith("java.") || packName.startsWith("javax.") || packName.startsWith("sun.")) {
				return obj;
			} else {//其他：指的是自定义的Bean
				return wrapBean(obj); // 循环引用会导致栈溢出
			}
		}
	}
	//--------------------Bean to Map end---------------------------------
	
	//--------------------Map to Bean start---------------------------------
	/**
	 * 将Map转换为Bean（增加异常判断）
	 * @param source
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object wrap(Map map,Class clz) 
			throws InstantiationException, IllegalAccessException {
		if (clz.getClass().isArray() || ReflectUtils.isCollection(clz)) {
			throw new IllegalArgumentException("Bean不能是集合也不能是数组");
		} else if (ReflectUtils.isBasic(clz.getClass())) {
			throw new IllegalArgumentException("Bean为基本数据类型，无需封装");
		} else {
			return wrapMap(map,clz);
		}
	}
	/**
	 *  将Map转换为Bean
	 * @param map 需要转换的Map
	 * @param clz Bean.class
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static Object wrapMap(Map map,Class clz) 
			throws InstantiationException, IllegalAccessException{
		Object obj = clz.newInstance();
		Map mapVal = model();
		Map<String, Method> mtds = ReflectUtils.getAllSetMethodByCache(clz);
		Iterator iter = mtds.entrySet().iterator();
		while(iter.hasNext()){//遍历set方法，不要遍历Map，防止Map里面还有多余的key-value需要作出额外处理
			Object val = null;
			Map.Entry entry = (Map.Entry) iter.next();
			String name = (String) entry.getKey();
			Method mtd = (Method) entry.getValue();
			try {
				ReflectUtils.setValueByMethod(obj, mtd,map.get(name));//这里不用towrap(map.get(name))处理，全部set map本身的value
			} catch (Exception cause) {
				throw new IllegalArgumentException("注入方法出错[" + clz + "]", cause);
			}
		}
		return obj;
	}
	//--------------------Map to Bean end---------------------------------
	
	//--------------------other start---------------------------------
	/**
	 * 创建Model Map，普遍用于对象数据的传递和处理
	 * @return
	 */
	public static Map<Object, Object> model() {
		return new HashMap<Object, Object>();
	}

	/**
	 * 对原有Map进行合并处理
	 * @param map
	 * @return
	 */
	public static Map<Object, Object> model(Map map) {
		if (map != null) {
			return new HashMap<Object, Object>(map);
		} else {
			return model();
		}
	}

	/**
	 * 创建ArrayList ， 普遍用于列表数据的传递和处理
	 * @return
	 */
	public static List list() {
		return new ArrayList();
	}

	/**
	 * List<Map>
	 * @param list
	 * @return
	 */
	public static List list(List<Map> list) {
		List newList = list();//返回的是ArrayList
		for (Map data : list) {
			newList.add(model(data));
		}
		return newList;
	}

	/**
	 * (key,null)填充Map
	 * @param dateMap
	 * @param keys
	 */
	public static void fillMapNull(Map dateMap, String... keys) {
		for (String key : keys) {
			if (dateMap.get(key) == null) {
				dateMap.put(key, null);
			}
		}
	}
	//--------------------other end---------------------------------
}