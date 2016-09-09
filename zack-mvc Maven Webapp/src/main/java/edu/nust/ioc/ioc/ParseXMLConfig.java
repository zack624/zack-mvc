package edu.nust.ioc.ioc;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;










import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.nust.ioc.description.BeanDescription;
import edu.nust.ioc.description.PropertyDescription;
import edu.nust.ioc.description.RefDescription;
import edu.nust.ioc.description.ValueDescription;
/**
 * 解析ioc配置文件
 * @author zack
 * @since  2016年9月9日
 * @version 1.0.0
 */
public class ParseXMLConfig {
	private static Map<String,BeanDescription> beansDescriptionMap = new HashMap<String, BeanDescription>();
	/**
	 * 
	 * @return beansDescription
	 */
	public static Map<String, BeanDescription> getMap(){
		return beansDescriptionMap;
	}
	
	public static BeanDescription getbeanDescriptionById(String id){
		return beansDescriptionMap.get(id);
	}
	/**
	 * 解析config.xml配置文件，将bean配置装载进map容器
	 * @param configPath
	 */
	public static void loadConfig(String configPath){
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(configPath));
			Element root = document.getRootElement();
			List<Element> beans = root.elements("bean");
			for (Element bean : beans) {
				String id = bean.attributeValue("id");
				String className = bean.attributeValue("class");
				String lazyInit = bean.attributeValue("lazy-init");
				String scope = bean.attributeValue("scope");
				String autowire = bean.attributeValue("autowire");
				BeanDescription beanDescription = new BeanDescription(id,className);
				if(lazyInit != null){
					beanDescription.setLazyInit(lazyInit);
				}
				if(scope != null){
					beanDescription.setScope(scope);
				}
				if(autowire != null){
					beanDescription.setAutoWire(autowire);
				}
				parseProperty(bean,beanDescription.getProperties());
				beansDescriptionMap.put(id,beanDescription);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析property标签
	 * @param bean
	 * @param properties
	 */
	private static void parseProperty(Element bean,
			List<PropertyDescription> properties) {
		List<Element> proElements = bean.elements("property");
		for (Element proElement : proElements) {
			String name = proElement.attributeValue("name");
			PropertyDescription propertyDescription = new PropertyDescription(name);
			//对value标签或ref标签解析
			List<Element> leaves = proElement.elements();
			Element leaf = leaves.get(0);
			if(leaf.getName().equals("value")){
				String valueText = leaf.getText();
				ValueDescription valueDescription = new ValueDescription(valueText);
				propertyDescription.setLeaf(valueDescription);
			}
			if(leaf.getName().equals("ref")){
				String refText = leaf.getText();
				RefDescription refDescription = new RefDescription(refText);
				propertyDescription.setLeaf(refDescription);
			}
			properties.add(propertyDescription);
		}
	}
}
