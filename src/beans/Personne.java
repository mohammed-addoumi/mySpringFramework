package beans;

import annotations.Autowired;
import annotations.Component;
import annotations.Qualifier;

@Component(name = "personneBean")
public class Personne {
	
	private int id;
	private String name;
	@Autowired
	@Qualifier("user")
	private User user;
	
	public Personne() {
		id = 10;
		name = "simo addoumi";	
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
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Personne [id=" + id + ", name=" + name + ", user=" + user + "]";
	}
	
	

}
