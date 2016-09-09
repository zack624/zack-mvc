package edu.nust.ioc.description;
/**
 * 配置文件中表示property标签的类
 * @author zack
 * @since  2016年9月7日
 * @version
 */
public class PropertyDescription {
	private String name;
	private LeafDescription leaf;
	
	
	public PropertyDescription() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PropertyDescription(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LeafDescription getLeaf() {
		return leaf;
	}
	public void setLeaf(LeafDescription leaf) {
		this.leaf = leaf;
	}
	
	

}
