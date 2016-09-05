package edu.nust.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nust.mvc.config.ZAnnotationManager;
import edu.nust.mvc.config.ZApplicationManager;
import edu.nust.mvc.config.ZClassManager;
import edu.nust.mvc.config.ZXMLManager;
import edu.nust.mvc.config.Zack;
import edu.nust.mvc.page.Page;
import edu.nust.mvc.page.ZPage;
import edu.nust.mvc.route.ZRoute;
import edu.nust.mvc.utils.PageUtils;
import edu.nust.mvc.utils.ReflectionUtils;
/**
 * zack-mvc的核心控制器，负责初始化框架，根据请求调用相应的controller及其方法
 * @author zack
 * @since  2016年9月4日
 * @version 1.0.0
 */
public class ZFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {		
//		String userConfigClass = filterConfig.getInitParameter("zconfig");
//		ZClassManager userConfig = ReflectionUtils.newInstance(ZClassManager.class,userConfigClass);
//		
//		if(null != userConfig){
//			userConfig.init();
//		}
		
//		ZXMLManager.loadXMLConfig();
		
//		ZAnnotationManager.parseAnnotation();
		ZApplicationManager.init();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String requestPath = req.getServletPath().substring(1);
		ZRoute route = Zack.getRoute(requestPath);
		if(null != route){
			executeMethod(route,req,resp);
		}else{
			chain.doFilter(request, response);
		}
	}

	private void executeMethod(ZRoute route, HttpServletRequest req,
			HttpServletResponse resp) {
		Object controller = route.getController();
		String viewName = ReflectionUtils.controllerMethodInvoke(controller, route.getMethod(), req, resp);
//		Page page = Zack.getPage(viewName,controller);
		//存在无返回页面的情况
		if(viewName != null){
			ZPage page = Zack.getPage(viewName);
			PageUtils.forward(page,req,resp);
		}
	}

	public void destroy() {
		
	}
	
}
