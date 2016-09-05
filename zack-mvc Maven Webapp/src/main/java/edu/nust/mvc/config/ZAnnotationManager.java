package edu.nust.mvc.config;

import java.lang.reflect.Method;
import java.util.List;







import edu.nust.mvc.annotation.ZAController;
import edu.nust.mvc.annotation.ZARequestMapping;
import edu.nust.mvc.page.Page;
import edu.nust.mvc.route.ZRoute;
import edu.nust.mvc.utils.ScanPackageUtils;
@Deprecated
public class ZAnnotationManager {
	
	public static void parseAnnotation(){
		Zack zack = Zack.getInstance();
		List<Class> controllerList = ScanPackageUtils.scanPackage(ZApplicationManager.getPackageName(),ZAController.class);
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
//		zack.addPage(new Page("index","edu.nust.mvc.test.HelloController","/index.jsp"));
//		zack.addPage(new Page("test","edu.nust.mvc.test.TestController","/test.jsp"));
	}
}
