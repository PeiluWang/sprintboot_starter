package org.pillow.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.pillow.common.exception.WebServerError;
import org.pillow.common.exception.WebServerException;

/**
 * 类型转换的辅助函数
 * @author peilu.wang
 *
 */
public class ModelUtil {
	
	public static<T> ArrayList<T> convertList(Iterable fromList, Class<T> toClass) throws Exception{
		ArrayList toList = new ArrayList();
		for(Object fromObject : fromList) {
			Object toObject = toClass.newInstance();
			updateModel(toObject, fromObject);
			toList.add(toObject);
		}
		return toList;
	}
	

	/**
	 * 如果新对象的值非空，则用新对象的属性值替换老对象的
	 * @param originObj
	 * @param updateObj
	 * @throws WebServerException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void updateModel(Object toObj, Object fromObj) throws WebServerException, NoSuchMethodException, Exception {
		Class toClass = toObj.getClass();
		Class fromClass = fromObj.getClass();
		//获取属性名称
		Field[] toFields = toClass.getDeclaredFields();
		Method[] toMethods = toClass.getDeclaredMethods();
		HashSet<String> toFieldNames = new HashSet<String>();
		HashMap<String, Class> toMethodTypeMap = new HashMap<String, Class>();
		for(Field field : toFields) {
			toFieldNames.add(field.getName());
		}
		for(Method method: toMethods) {
			String methodName = method.getName();
			if(!methodName.startsWith("set")) {
				continue;
			}
			Class[] types = method.getParameterTypes();
			if(types.length!=1) {
				throw new WebServerException(WebServerError.INVALID_INPUT,"bean对象的set方法参数不为1： "+types.length);
			}
			toMethodTypeMap.put(methodName,types[0]);
		}

		Field[] nFields = fromClass.getDeclaredFields();
		for(Field field : nFields) {
			String fieldName = field.getName();
			//判断如果属性名称相同，且新对象的属性值不为空，则更新原变量
			if(toFieldNames.contains(fieldName)) {
				String getMethodName = genMethodName("get", fieldName);
				Method getMethod = fromClass.getMethod(getMethodName);
				Object updateValue = getMethod.invoke(fromObj);
				if(updateValue==null) {
					continue;
				}
				String setMethodName = genMethodName("set", fieldName);
				Class type = toMethodTypeMap.get(setMethodName);
				Method setMethod = toClass.getMethod(setMethodName, type);
				setMethod.invoke(toObj, updateValue);
			}
		}
		
	}
	
	/**
	 * 根据属性名称获取方法名称
	 * 例：user -> getUser
	 * @param filedName
	 * 属性名称
	 * @param prefix
	 * 前缀（get, set）
	 * @return
	 * 方法名称
	 * @throws WebServerException 
	 */
	private static String genMethodName(String prefix, String filedName) throws WebServerException {
		if(filedName.isEmpty()) {
			throw new WebServerException(WebServerError.INVALID_INPUT,"propertyName is null!");
		}
		if(filedName.length()<=1) {
			throw new WebServerException(WebServerError.INVALID_INPUT,"propertyName is too short! length is:"+filedName.length());
		}
		String firstLetter = filedName.substring(0, 1).toUpperCase();
		return prefix +firstLetter + filedName.substring(1);
	}
	
}
