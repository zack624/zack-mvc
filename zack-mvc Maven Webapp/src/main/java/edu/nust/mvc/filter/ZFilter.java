package edu.nust.mvc.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import edu.nust.ioc.ioc.IOCContianer;
import edu.nust.mvc.config.ZApplicationManager;
import edu.nust.mvc.config.Zack;
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
	private static final Logger LOGGER = Logger.getLogger(ZFilter.class.getName());
	
	public void init(FilterConfig filterConfig) throws ServletException {		
		//先执行ioc容器初始化，再初始化mvc配置。
		IOCContianer.init("src/main/resources/beans.xml");
		LOGGER.info("Init:beans config init");
		ZApplicationManager.init();
		LOGGER.info("Init:application config init");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String requestPath = req.getServletPath().substring(1);
		LOGGER.info("Request URL:"+req.getRequestURI());
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
		//存在无返回页面的情况
		if(viewName != null){
			ZPage page = Zack.getPage(viewName);
			PageUtils.forward(page,req,resp);
			LOGGER.info("Forward:" + page.getPrefix()+viewName+page.getSuffix());
		}
	}

	public void destroy() {
		
	}
	
}
