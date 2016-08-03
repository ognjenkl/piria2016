package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.LoginDAO;
import dto.UserDTO;

/**
 * @author ognjen
 *
 */
@SessionScoped
@ManagedBean (name="login", eager=true)
public class LoginBean {
	
	UserDTO user = null;
	String username;
	String password;
	String usernameRegister;
	String passwordRegister;
	String email;
	boolean loggedIn;
	
	public LoginBean(){
		username = "";
		password = "";
		email = "";
		loggedIn = false;
		user = null;
	}

	public String login(){
		UserDTO loggedUser = null;
		String retVal = null;
		
		loggedUser = LoginDAO.login(username, password);
		if(loggedUser != null){
			user = loggedUser;
			loggedIn = true;

			switch(loggedUser.getPrivilege()){
				case 1:
//					retVal = "admin?faces-redirect=true";
					retVal = null;
					break;
				case 2:
//					retVal = "superuser?faces-redirect=true";		
					retVal = null;
					break;
				case 3:
//					retVal = "user?faces-redirect=true";
					retVal = null;
					break;
				default:
//					retVal = "guest?faces-redirect=true";
					retVal = null;
					break;
			}
		}
		else{
			retVal = "guest&faces-redirect=true";
			loggedIn = false;
		}
		
		username = "";
		password = "";
		email = "";
		
		return retVal;
	}
	
	public String logout(){
		((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		String retVal = "guest?faces-redirect=true";
		loggedIn = false;
		user = null;
		return retVal;
	}
	
	public String register(){
		String retVal = "guest?faces-redirect=true";
		if(LoginDAO.register(username, password, email)){
			retVal = login();
		}
		
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsernameRegister() {
		return usernameRegister;
	}

	public void setUsernameRegister(String usernameRegister) {
		this.usernameRegister = usernameRegister;
	}

	public String getPasswordRegister() {
		return passwordRegister;
	}

	public void setPasswordRegister(String passwordRegister) {
		this.passwordRegister = passwordRegister;
	}

	
	
	
	
}
