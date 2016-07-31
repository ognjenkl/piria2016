package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dto.UserDTO;

/**
 * @author ognjen
 *
 */
@SessionScoped
@ManagedBean (name="login", eager=true)
public class LoginBean {
	UserDTO user;
	String username;
	String password;
	
	public LoginBean(){
		username = "";
		password = "";
		user = null;
	}

	public String login(){
		String retVal = "admin";
		
		return retVal;
	}
	
	public String logout(){
		String retVal = "guset";
		
		return retVal;
	}
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
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
