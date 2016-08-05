package dto;

/**
 * @author ognjen
 *
 */
public class UserDTO {
	
	String username;
	String password;
	String email;
	int privilege;
	
	//cuvanje podesavanja za sljedeci pristup
	String language;
	
	public UserDTO() {
		username = null;
		password = null;
		email = null;
		privilege = -1;
		language = null;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


	
}
