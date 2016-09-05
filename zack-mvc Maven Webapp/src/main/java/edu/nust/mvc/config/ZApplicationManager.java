package edu.nust.mvc.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.nust.mvc.annotation.ZAController;
import edu.nust.mvc.annotation.ZARequestMapping;
import edu.nust.mvc.page.ZPage;
import edu.nust.mvc.route.ZRoute;
import edu.nust.mvc.utils.ScanPackageUtils;

/**
 * zack-mvc的配置文件及注解解析器
 * @author zack
 * @since  2016年9月4日
 * @version 1.0.0
 */
public class ZApplicationManager {
	private static String classpath; 
	private static Document document;
	private static Zack zack = Zack.getInstance();
	static{
		try{
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
					.getResources("applicationContext.xml");
			while(urls.hasMoreElements()){
				URL url = urls.nextElement();
				if(url.getProtocol().equals("file")){
					try{
						classpath = URLDecoder.decode(url.getFile(),"utf-8");
						SAXReader reader = new SAXReader();
						document = reader.read(new File(classpath));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * 通过组件标签，获得包名称
	 * @return
	 */
	public static String getPackageName(){
		Element root = document.getRootElement();
		Element component = root.element("component-scan");
		String packageName = component.attribute("base-package").getValue();
		return packageName;
	}
	/**
	 * 设置返回视图的前后缀
	 */
	public static void setPagePrefixAndSuffix(){
		Element root = document.getRootElement();
		String prefix = null;
		String suffix = null; 
		Element bean = root.element("bean");
		List<Element> properties = bean.elements("property");
		for (Element property : properties) {
			String name = property.attribute("name").getValue();
			String value = property.attribute("value").getValue();
			if(name.equals("prefix")){
				prefix = value;
			}else if(name.equals("suffix")){
				suffix = value;
			}
		}
		zack.setPagePrefixAndSuffix(prefix, suffix);
	}
	/**
	 * 通过注解获得所有Controller类及其方法
	 */
	public static void parseAnnotation(){
		List<Class> controllerList = ScanPackageUtils.scanPackage(getPackageName(),ZAController.class);
		for (Class controller : controllerList) {
			Method[] methods = controller.getMethods();
			for (Method method : methods) {
				ZARequestMapping requestMapping = method.getAnnotation(ZARequestMapping.class);
				if(requestMapping != null){
					ZRoute r = new ZRoute(requestMapping.value(),controller.getName(), method.getName());
					zack.addRoute(r);
				}
			}
		}
	}
	
	public static void init(){
		parseAnnotation();
		setPagePrefixAndSuffix();
	}
}
