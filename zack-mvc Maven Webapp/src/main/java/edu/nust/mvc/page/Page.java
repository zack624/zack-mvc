package edu.nust.mvc.page;

import edu.nust.mvc.controller.ZControllerFactory;
@Deprecated
public class Page {
	private String viewName;
	private Object controller;
	private String resultView;
	
	public Page(String viewName, String controllerPath, String resultView) {
		super();
		this.viewName = viewName;
		this.controller = ZControllerFactory.getController(controllerPath);
		this.resultView = resultView;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String path) {
		this.viewName = path;
	}
	public Object getController() {
		return controller;
	}
	public void setController(Object controller) {
		this.controller = controller;
	}
	public String getResultView() {
		return resultView;
	}
	public void setResultView(String resultView) {
		this.resultView = resultView;
	}
	
}
