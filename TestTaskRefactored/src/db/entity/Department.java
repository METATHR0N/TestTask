package db.entity;

import java.io.Serializable;

public class Department implements Serializable{

	private static final long serialVersionUID = 3386122917720604677L;

	private int id;
	
	private String name;

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
	
	@Override
	public String toString() {
		return "Department [id=" + id + ", name = " + name +"]";
	}
	
}
