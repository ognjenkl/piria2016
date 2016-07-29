package dto;

/**
 * @author ognjen
 *
 */
public class UserDTO {
	
	String username;
	String password;
	
	public UserDTO() {
		username = null;
		password = null;
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

	
}
