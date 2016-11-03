package dto;

import java.util.Calendar;

/**
 * @author ognjen
 *
 */
public class UserDTO {
	
	int id;
	String firstName;
	String lastName;
	String socialNo;
	String email;
	String picture;
	String username;
	String password;
	int privilege;
	Calendar registered;
	boolean active;
	
	boolean editable;
	
	
	public UserDTO() {
		id = -1;
		firstName = null;
		lastName = null;
		socialNo = null;
		email = null;
		picture = null;
		username = null;
		password = null;
		privilege = 10;
		active = false;
		registered = null;
		
		editable = false;
	}

	
	public UserDTO(int id, String firstName, String lastName, String socialNo, String email, String picture, String username,
			String password, int privilege, boolean active, boolean editable) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.socialNo = socialNo;
		this.email = email;
		this.picture = picture;
		this.username = username;
		this.password = password;
		this.privilege = privilege;
		this.active = active;
		this.editable = editable;
	}

	





	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSocialNo() {
		return socialNo;
	}

	public void setSocialNo(String socialNo) {
		this.socialNo = socialNo;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}



	public Calendar getRegistered() {
		return registered;
	}


	public void setRegistered(Calendar registered) {
		this.registered = registered;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return firstName + " " + lastName + " " + socialNo + " " + email + " " + picture + " " + username + " " + password + " " + privilege + " " + active + " " + editable;
	}

	
}
