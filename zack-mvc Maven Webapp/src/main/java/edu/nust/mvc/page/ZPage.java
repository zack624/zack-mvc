package edu.nust.mvc.page;
/**
 * 框架配置的页面实体
 * @author zack
 * @since  2016年9月4日
 * @version 1.0.0
 */
public class ZPage {
	private String prefix;
	private String suffix;
	private String viewName;
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
}
