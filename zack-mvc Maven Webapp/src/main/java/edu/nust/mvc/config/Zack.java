package edu.nust.mvc.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nust.mvc.controller.ZController;
import edu.nust.mvc.page.Page;
import edu.nust.mvc.page.ZPage;
import edu.nust.mvc.route.ZRoute;

public class Zack {
	
	private static List<ZRoute> routes = new ArrayList<ZRoute>();
	private static ZPage newPage = new ZPage();
	@Deprecated
	private static List<Page> pages = new ArrayList<Page>();
	
	//单例，静态内部类
	private Zack() {}
	
	public static class ZackHolder{
		private static final Zack instance = new Zack();
	}

	public static final Zack getInstance(){
		return ZackHolder.instance;
	}
	
	public void addRoute(ZRoute r){
		routes.add(r);
	}
	@Deprecated
	public void addPage(Page p){
		pages.add(p);
	}
	public void setPagePrefixAndSuffix(String prefix,String suffix){
		newPage.setPrefix(prefix);
		newPage.setSuffix(suffix);
	}
	public List<ZRoute> getRoutes() {
		return routes;
	}
	@Deprecated
	public List<Page> getPages() {
		return pages;
	}
	
	public static ZRoute getRoute(String requestUri){
		for(ZRoute r : routes){
			if(r.getPath().equals(requestUri)){
				return r;
			}
		}
		return null;
	}
	
	public static boolean containsPath(String requestUri) {
		for(ZRoute r : routes){
			if(r.getPath().equals(requestUri)){
				return true;
			}
		}
		return false;
	}
	@Deprecated
	public static Page getPage(String viewName,ZController controller){
		for(Page p : pages){
			if(p.getViewName().equals(viewName) && p.getController() == controller){
				return p;
			}
		}
		return null;
	}
	public static ZPage getPage(String viewName){
		newPage.setViewName(viewName);
		return newPage;
	}
}
