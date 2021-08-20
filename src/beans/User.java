package beans;

import annotations.Component;

@Component(name = "userBean")
public class User {

	private int id;
	private String name;
//
	
	public User() {}

	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	//	this.names = names;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}*/


	@Override
	public String toString() {
		return "User[id=" + id + ", name=" + name + "]";
	}

	


}
