package edu.nust.mvc.route;

import edu.nust.ioc.ioc.IOCContianer;
import edu.nust.mvc.controller.ZControllerFactory;
/**
 * 框架配置的路由实体，即联系请求路径和controller及其方法
 * @author zack
 * @since  2016年9月4日
 * @version 1.0.0
 */
public class ZRoute {
	private String path;
	private Object controller;
	private String method;
	
	
	public ZRoute(String path, String controllerPath, String method) {
		super();
		this.path = path;
		//使用ioc提供的controller
		this.controller = IOCContianer.getBeanById("controller");
		this.method = method;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Object getController() {
		return controller;
	}
	public void setController(Object controller) {
		this.controller = controller;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
