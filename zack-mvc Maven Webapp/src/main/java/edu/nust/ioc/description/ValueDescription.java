package edu.nust.ioc.description;

public class ValueDescription implements LeafDescription{
	private String value;

	
	public ValueDescription(String value) {
		super();
		this.value = value;
	}

	public ValueDescription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
