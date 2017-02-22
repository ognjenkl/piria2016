package dto;

import java.io.Serializable;

public class ReportDTO implements Serializable{

	private static final long serialVersionUID = 666302477318600389L;

	private String name;
	private Double value;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
