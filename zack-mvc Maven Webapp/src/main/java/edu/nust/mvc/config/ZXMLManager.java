package edu.nust.mvc.config;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.nust.mvc.page.Page;
import edu.nust.mvc.route.ZRoute;


public class ZXMLManager {
	
	public static void loadXMLConfig(){
		Zack zack = Zack.getInstance();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File("src/main/resources/zack-mvc.xml"));
			Element root = document.getRootElement();
			List<Element> routeList = root.elements("route");
			for (Element route : routeList) {
				String path = route.attribute("path").getValue();
				String controllerPath = route.attributeValue("controllerPath");
				String method = route.attributeValue("method");
				zack.addRoute(new ZRoute(path, controllerPath, method));
			}
			
			List<Element> pageList = root.elements("page");
			for (Element page : pageList) {
				String viewName = page.attributeValue("viewName");
				String controllerPath = page.attributeValue("controllerPath");
				String resultView = page.attributeValue("resultView");
				zack.addPage(new Page(viewName, controllerPath, resultView));
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
