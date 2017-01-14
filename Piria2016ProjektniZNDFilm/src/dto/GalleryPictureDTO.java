package dto;

import java.io.Serializable;
import java.util.Date;

public class GalleryPictureDTO implements Serializable{

	private static final long serialVersionUID = 1305272435437943681L;
	
	Integer id;
	String name;
	String location;
	Date createDate;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
		

}
