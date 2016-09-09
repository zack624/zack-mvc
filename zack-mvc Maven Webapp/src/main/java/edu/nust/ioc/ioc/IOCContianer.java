package edu.nust.ioc.ioc;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import edu.nust.ioc.description.BeanDescription;
import edu.nust.ioc.description.LeafDescription;
import edu.nust.ioc.description.PropertyDescription;
import edu.nust.ioc.description.RefDescription;
import edu.nust.ioc.description.ValueDescription;
/**
 * ioc容器的核心部分，负责管理所有的bean实例。
 * @author zack
 * @since  2016年9月9日
 * @version 1.0.0
 */
public class IOCContianer {
	private static final Logger LOGGER = Logger.getLogger(IOCContianer.class.getName());
	
	private static Map<String,Object> beansMap = new HashMap<String, Object>();
	/**
	 * 供外部获取bean实例
	 * @param id
	 * @return
	 */
	public static Object getBeanById(String id){
		Object bean = beansMap.get(id);
		if(bean == null){
			return null;
		}
		if(bean instanceof BeanDescription){
			BeanDescription beanDescription = (BeanDescription) bean;
			if(beanDescription.isPrototype()){
				return newInstance(beanDescription);
			}else if(beanDescription.isLazyInit()){
				Object o = newInstance(beanDescription);
				beansMap.put(id,o);
				return o;
			}
		}
		return bean;
	}
	
	/**
	 * 初始化ioc容器，将需要实例化的bean放入beansMap中
	 * @param configPath
	 */
	public static void init(String configPath){
		ParseXMLConfig.loadConfig(configPath);
		LOGGER.info("读取ioc配置文件");
		Map<String,BeanDescription> beansDescriptionMap = ParseXMLConfig.getMap();
		Collection<BeanDescription> beansDescription = beansDescriptionMap.values();
		for (BeanDescription beanDescription : beansDescription) {
			String id = beanDescription.getId();
			Object o = getBeanById(id);
			if(null != o){
				continue;
			}
			
			//懒加载或者原型模式时，将beanDescription直接入入beansMap中。
			if(beanDescription.isLazyInit() || beanDescription.isPrototype()){
				beansMap.put(id,beanDescription);
			}else{
				Object bean = newInstance(beanDescription);
				beansMap.put(id,bean);
			}
		}
	}
	/**
	 * 实例化bean
	 * @param beanDescription
	 */
	private static Object newInstance(BeanDescription beanDescription) {
		
		String className = beanDescription.getClassName();
		List<PropertyDescription> properties = beanDescription.getProperties();
		Object bean = null;
		try {
			Class beanClass = Thread.currentThread().getContextClassLoader().loadClass(className);
			bean = beanClass.newInstance();
			if(!properties.isEmpty()){
				injectField(bean,beanClass,properties);
			}else if(beanDescription.isAutowireByName()){
				autowireField(bean,beanClass);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		LOGGER.info("创建Bean:"+bean);
		return bean;
	}
	/**
	 * 单独处理autowire为byName类型时的字段注入
	 * @param bean
	 * @param beanClass
	 */
	private static void autowireField(Object bean, Class beanClass) {
		Field[] fields = beanClass.getDeclaredFields();
		try {
			for (Field field : fields) {
				if(field.getType().isPrimitive()){
					continue;
				}else if(field.getType().getName().contains("String")){
					continue;
				}
				injectRef(bean, field, field.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 注入bean的字段值
	 * @param bean
	 * @param beanClass
	 * @param properties 
	 */
	private static void injectField(Object bean, Class beanClass, List<PropertyDescription> properties) {
		try{
			for (PropertyDescription proDescription : properties) {
				String name = proDescription.getName();
				LeafDescription leaf = proDescription.getLeaf();
				//处理value类型
				if(leaf instanceof ValueDescription){
					ValueDescription value = (ValueDescription) leaf;
					injectValue(bean, beanClass,name,value);
				//处理ref类型
				}else if(leaf instanceof RefDescription){
					RefDescription ref = (RefDescription) leaf;
					Field field = beanClass.getDeclaredField(name);
					Object refObject = getBeanById(ref.getRef());
					injectRef(bean,field, ref.getRef());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 当property是value类型时。
	 */
	private static void injectValue(Object bean,Class beanClass,String name,
			ValueDescription leaf){
		try{
			String valueText = leaf.getValue();
			Field field = beanClass.getDeclaredField(name);
			String fieldType = field.getType().getName();
			field.setAccessible(true);
			if(fieldType.contains("String")){
				field.set(bean,valueText);
			}else if(fieldType.contains("int")){
				field.set(bean,Integer.parseInt(valueText));
			}else if(fieldType.contains("boolean")){
				field.set(bean,Boolean.parseBoolean(valueText));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 当autowire为byName和property子标签为ref时注入字段
	 * @param field
	 * @param refId
	 */
	private static void injectRef(Object bean,Field field,String refId){
		try{
			field.setAccessible(true);
			Object refObject = getBeanById(refId);
			if(null != refObject){
				field.set(bean,refObject);
				return;
			}
			BeanDescription beanD = ParseXMLConfig.getbeanDescriptionById(refId);
			if(null != beanD){
				if(beanD.isLazyInit() || beanD.isPrototype()){
					beansMap.put(refId,beanD);
				}else{
					Object o = newInstance(beanD);
					beansMap.put(refId,o);
					field.set(bean,o);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
