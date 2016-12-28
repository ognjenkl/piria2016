package dto;

import java.io.Serializable;

public class GenreDTO implements Serializable {

	private static final long serialVersionUID = 7765270189520689080L;
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

	
	
}
