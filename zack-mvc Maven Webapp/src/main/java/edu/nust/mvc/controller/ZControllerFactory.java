package edu.nust.mvc.controller;

import java.util.HashMap;
import java.util.Map;
/**
 * 确保每个controller只有一个副本
 * @author zack
 * @since  2016年8月29日
 * @version 1.0.0
 */
public class ZControllerFactory {
	private static Map<String,Object> controllerMap = new HashMap<String, Object>();
	/**
	 * Abstract Factory Pattern
	 * @param classPath
	 * @return
	 */
	public static Object getController(String classPath){
		Object controller = controllerMap.get(classPath);
		if(controller == null){
			try {
				Class clazz = Class.forName(classPath);
				controller = (Object) clazz.newInstance();
				controllerMap.put(classPath,controller);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return controller;
	}
}
