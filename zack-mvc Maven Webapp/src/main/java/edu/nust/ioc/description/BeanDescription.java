package edu.nust.ioc.description;

import java.util.ArrayList;
import java.util.List;
/**
 * 配置文件中表示bean标签的类
 * @author zack
 * @since  2016年9月7日
 * @version
 */
public class BeanDescription {
	private String id;
	private String className;
	private boolean lazyInit;
	private String scope;
	private String autoWire;
	private List<PropertyDescription> properties = new ArrayList<PropertyDescription>();
	
	public BeanDescription() {
		super();
	}
	
	
	public BeanDescription(String id, String className) {
		super();
		this.id = id;
		this.className = className;
	}


	public String getAutoWire() {
		return autoWire;
	}


	public void setAutoWire(String autoWire) {
		this.autoWire = autoWire;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public boolean isLazyInit() {
		return lazyInit;
	}
	public void setLazyInit(String lazyInit) {
		if(lazyInit.equals("true")){
			this.lazyInit = true;
		}
	}
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void addProperty(PropertyDescription property){
		this.properties.add(property);
	}


	public List<PropertyDescription> getProperties() {
		return properties;
	}


	public void setProperties(List<PropertyDescription> properties) {
		this.properties = properties;
	}


	public boolean isSingleton() {
		if(this.scope == null || this.scope.equals("singleton")){
			return true;
		}
		return false;
	}
	
	public boolean isPrototype(){
		if(this.scope != null && this.scope.equals("prototype")){
			return true;
		}
		return false;
	}
	public boolean isAutowireByName(){
		if(this.autoWire != null && this.autoWire.equals("byName")){
			return true;
		}
		return false;
	}
	
}
