package com.paleo.blog.tools.reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * @author hc 反射工具 进行了预存优化 --- 需要解决问题 类的动态加载？
 * @author Paleo 优化其转化效率，并且可以更好地遵循Java Bean的规范
 */
public class ReflectUtils {
	/**
	 * <p>注意，由于这里没有加入缓存淘汰的算法，缓存没有覆盖，且key对应的value一直是固定的。</p>
	 * <p>虽然会增加key-value，但是此类所有方法都是根据key取value，没有遍历操作，所以不会产生读错误。</p>
	 * <p>综上：这里暂时不需要同步数据，做成线程安全。（但是没有缓存淘汰，全部存入会占用较多空间。）</p>
	 * <p>如果以后考虑扩展此工具类，或者说有必要控制缓存大小，加入缓存淘汰算法。则有必要考虑线程安全</p>
	 * <p>特此说明。                             ——Paleo</p>
	 */
	/**
	 * <p>缓存class实例域的Map，非线程安全，Field[]用数组，效率高</p>
	 */
	private final static Map<Class<? extends Object>, Field[]> ALL_FIELDS_MAP = new HashMap<Class<? extends Object>, Field[]>();
	/**
	 * <p>缓存class所有getters的map，非线程安全，HashMap的key作为数组，value作为get方法对应的变量名。</p>
	 * <p>疑问：但是Method作为为key是否会重复？？？</p>
	 * <p>查看Method的equals方法，returnType，parameterTypes和parameterTypes个数相同（即方法表相同），还有类相同(类名是否属于方法表？？)，所以Method不会重复。</p>
	 * <p>同理：一个Bean里面，属性名也不可能是一样的，同理可以用name作为主键.= =||所以版本2.0我换为变量名作key了。。。</p>
	 */
	private final static Map<Class<? extends Object>, HashMap<String, Method>> ALL_GETMETHOD_MAP = new HashMap<Class<? extends Object>, HashMap<String, Method>>();
	/**
	 * 缓存class所有setters的map，非线程安全，HashMap的key对应变量，value作为set方法。
	 */
	private final static Map<Class<? extends Object>, HashMap<String, Method>> ALL_SETMETHOD_MAP = new HashMap<Class<? extends Object>, HashMap<String, Method>>();

	
	//--------------------getter相关 start---------------------------
	/**
	 * 从缓存获取clz的Field[]，如果缓存为null，则通过反射机制获取
	 * @param clz 限定了只能是继承自Object的类的class
	 * @return
	 */
	public static Field[] getAllFieldByCache(Class<? extends Object> clz) {
		//从缓存获取实例域
		Field[] fields = ALL_FIELDS_MAP.get(clz);
		if (fields == null) {
			//反射机制获取
			List<Field> fiedList = getAllField(clz);
			fields = fiedList.toArray(new Field[fiedList.size()]);//转数组
			ALL_FIELDS_MAP.put(clz, fields);//放入缓存
		}
		return fields;
	}

	/**
	 * 从缓存获取clz的Method[]，如果缓存为null，则通过反射机制获取
	 * @param clz 限定了只能是继承自Object的类的class
	 * @return
	 */
	public static HashMap<String, Method> getAllGetMethodByCache(Class<? extends Object> clz) {
		//从缓存获取方法
		HashMap<String, Method> methods = ALL_GETMETHOD_MAP.get(clz);
		if (methods == null) {
			//反射机制获取
			methods = getAllGetMethod(clz);
			ALL_GETMETHOD_MAP.put(clz, methods);//放入缓存
		}
		return methods;
	}


	public static HashMap<String, Method> getAllGetMethod(Class<? extends Object> clz) {
		Field[] fields = getAllFieldByCache(clz);
		PropertyDescriptor pd = null;
		Method getMethod = null;
		HashMap<String, Method> map = new HashMap<String, Method>();
		for(Field field:fields){
			try {
				pd = new PropertyDescriptor(field.getName(), clz);
				getMethod = pd.getReadMethod();//获得get方法
				map.put(field.getName() ,getMethod );
			} catch (IntrospectionException e) {
//				System.out.println("IntrospectionException!");
			}
		}
		return map;
	}
	/**
	 * 通过get方法获取值
	 * @param obj bean
	 * @param mtd get方法
	 * @return
	 * @throws Exception
	 */
	public static Object getValueByMethod(Object obj, Method mtd)
		throws Exception {
		Object value = null;
		if (mtd != null) {
			value = mtd.invoke(obj);
		}
		return value;
	}
	//--------------------getter相关 end---------------------------
	


	//--------------------setter相关 start---------------------------
	/**
	 * 通过缓存获取所有set方法
	 * @param clz
	 * @return
	 */
	public static HashMap<String, Method> getAllSetMethodByCache(Class<? extends Object> clz) {
		//从缓存获取方法
		HashMap<String, Method> methods = ALL_SETMETHOD_MAP.get(clz);
		if (methods == null) {
			//反射机制获取
			methods = getAllSetMethod(clz);
			ALL_SETMETHOD_MAP.put(clz, methods);//放入缓存
		}
		return methods;
	}
	/**
	 * 获取所有set方法（反射机制）
	 * @param clz
	 * @return
	 */
	public static HashMap<String, Method> getAllSetMethod(Class<? extends Object> clz) {
		Field[] fields = getAllFieldByCache(clz);
		PropertyDescriptor pd = null;
		Method setMethod = null;
		HashMap<String, Method> map = new HashMap<String, Method>();
		for(Field field:fields){
			try {
				pd = new PropertyDescriptor(field.getName(), clz);
				setMethod = pd.getWriteMethod();//获得get方法
				map.put(field.getName(),setMethod);
			} catch (IntrospectionException e) {//catch异常，PropertyDescriptor如果异常，跳过此属性
//				System.out.println("IntrospectionException!");
			}
		}
		return map;
	}
	/**
	 * 调用set方法
	 * @param obj clz实例化的对象obj
	 * @param mtd set方法
	 * @param value 要set的值
	 * @throws Exception
	 */
	public static void setValueByMethod(Object obj, Method mtd,Object value)
			throws Exception {
			if (mtd != null) {
				mtd.invoke(obj, value);
			}
		}
	

	/**
	 * 设置obj对象Field field的属性值
	 * @param obj
	 * @param field
	 * @param value
	 * @throws Exception
	 */
	public static void setValueByField(Object obj, Field field, Object value)
		throws Exception {
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);//可访问
			field.set(obj, value);
			field.setAccessible(false);//禁止访问
		}
	}
	//--------------------setter相关 end---------------------------
	
	//--------------------Field相关 start---------------------------
	/**
	 * 通过域名fieldName找到clz的Field对象
	 * @param clz
	 * @param fieldName
	 * @return
	 */
	public static Field findFieldByName(Class clz, String fieldName) {
		if (StringUtils.isNotEmpty(fieldName)) {
			Field[] fields = getAllFieldByCache(clz);
			for (Field field : fields) {
				if (fieldName.equals(field.getName())) {
					return field;
				}
			}
		}
		return null;
	}

	/**
	 * 通过反射机制，获取obj对象fieldName的Field 
	 * @param obj
	 * @param fieldName
	 * @return obj的继承层次从子类到父类第一个name为fieldName的Field对象
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		//遍历obj.getClass()的继承层次，从子类（低层次）开始获取。根据名字获取实例域。
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 通过反射机制，获取clz的所有Field as List
	 * @param clz 限定了只能是继承自Object的类的class
	 * @return
	 */
	public static List<Field> getAllField(Class<? extends Object> clz) {
		List<Field> allFieldList = new ArrayList<Field>();
		//遍历clz的继承层次直到万类之祖Object，获取他所有的父类的实例域
		for (Class<?> superClass = clz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			//superClass.getDeclaredFields:获取superClass的所有实例域
			//This includes public, protected, default (package) access, and private fields, but excludes inherited fields. 
			List<Field> filedList = Arrays.asList(superClass.getDeclaredFields());//注意区分superClass.getFields:获取的是当前类和他所有父类的public方法
			allFieldList.addAll(filedList);
		}
		return allFieldList;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
		throws Exception {
		Field field = getFieldByFieldName(obj, fieldName);
		return getValueByField(obj, field);
	}
	
	/**
	 * 通过Field
	 * @param obj
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public static Object getValueByField(Object obj, Field field)
		throws Exception {
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {//是否可访问
				value = field.get(obj);
			} else {
				field.setAccessible(true);//设置可访问
				value = field.get(obj);
				field.setAccessible(false);//设置不可访问
			}
		}
		return value;
	}
	/**
	 * 设置obj对象名为fieldName的Field的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void setValueByFieldName(Object obj, String fieldName,Object value) 
		throws Exception {
		Field field = obj.getClass().getDeclaredField(fieldName);
		setValueByField(obj, field, value);
	}
	//--------------------Field相关 end---------------------------
	
	//--------------------others相关 start---------------------------
	/**
	 * 判断是否为基本数据类型 包括封装类型 NULL
	 * @param clz
	 * @return
	 */
	public static boolean isBasic(Class<? extends Object> clz) {
		if (clz == null || clz.isPrimitive() || clz == String.class
				|| clz == Long.class || clz == Integer.class
				|| clz == Double.class || clz == Float.class
				|| clz == Boolean.class || clz == Character.class
				|| clz == Byte.class || clz == Short.class || clz == Void.class) {
			return true;
		}
		return false;
	}

	/**
	 * 判断obj是否属于集合，如List/Set等
	 * @param obj
	 * @return
	 */
	public static boolean isCollection(Object obj) {
		return obj instanceof Collection;
	}
	//--------------------others相关 end---------------------------
}