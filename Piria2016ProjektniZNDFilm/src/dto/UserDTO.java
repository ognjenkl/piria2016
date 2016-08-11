package dto;

/**
 * @author ognjen
 *
 */
public class UserDTO {
	
	String firstName;
	String lastName;
	String socialNo;
	String email;
	String picture;
	String username;
	String password;
	int privilege;
	
	public UserDTO() {
		firstName = null;
		lastName = null;
		socialNo = null;
		email = null;
		picture = null;
		username = null;
		password = null;
		privilege = 10;
	}

	
	
	public UserDTO(String firstName, String lastName, String socialNo, String email, String picture, String username,
			String password, int privilege) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.socialNo = socialNo;
		this.email = email;
		this.picture = picture;
		this.username = username;
		this.password = password;
		this.privilege = privilege;
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



	
}
