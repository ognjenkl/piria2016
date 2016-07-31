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
	boolean loggedIn;
	
	public LoginBean(){
		username = "";
		password = "";
		loggedIn = false;
		user = null;
	}

	public String login(){
		String retVal = "admin?faces-redirect=true";
		loggedIn = true;
		
		return retVal;
	}
	
	public String logout(){
		String retVal = "guest?faces-redirect=true";
		loggedIn = false;
		
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

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	
	
}
