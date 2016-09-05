package edu.nust.mvc.test;

import edu.nust.mvc.config.ZClassManager;
import edu.nust.mvc.config.Zack;
import edu.nust.mvc.page.Page;
import edu.nust.mvc.route.ZRoute;

public class UserConfig implements ZClassManager {

	public void init() {
		Zack zack = Zack.getInstance();
		
		
		ZRoute r1 = new ZRoute("hello","edu.nust.mvc.test.HelloController","helloexecute");
		ZRoute r2 = new ZRoute("test","edu.nust.mvc.test.HelloController","testexecute");
		ZRoute r3 = new ZRoute("test2","edu.nust.mvc.test.TestController","testexecute");
		zack.addRoute(r1);
		zack.addRoute(r2);
		zack.addRoute(r3);
		zack.addPage(new Page("index","edu.nust.mvc.test.HelloController","/index.jsp"));
		zack.addPage(new Page("test","edu.nust.mvc.test.TestController","/test.jsp"));
	}

}
