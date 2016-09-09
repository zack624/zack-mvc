package edu.nust.ioc.description;

public class RefDescription implements LeafDescription{
	private String ref;

	public RefDescription(String ref) {
		super();
		this.ref = ref;
	}

	public RefDescription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
	
}
