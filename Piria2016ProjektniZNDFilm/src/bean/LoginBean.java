package bean;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
	
	String oldLanguage;
	
	//localization
	Locale locale;
	Map<String, String> availableItems;
	
	public LoginBean(){
		username = "";
		password = "";
		email = "";
		loggedIn = false;
		user = null;
		availableItems = new TreeMap<>();
		locale = null;
		
		oldLanguage = null;
		
	}

	@PostConstruct
	public void init(){
		
		locale =  FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
		setLanguage("sr");
		
		availableItems.put("en","English");
		availableItems.put("sr", "Srpski");
		availableItems.put("fr", "Francais");
		availableItems.put("de", "Deutsche");
		
	}
	
	public String login(){
		UserDTO loggedUser = null;
		String retVal = null;

		
		Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
		Cookie c = (Cookie)map.get("language");
		if(c != null){
			System.out.println("coookieeee " + c.getValue());
			setLanguage(c.getValue());
		}
	
		loggedUser = LoginDAO.login(username, password);
		if(loggedUser != null){
			user = loggedUser;
			loggedIn = true;

			if(oldLanguage != null){
				System.out.println("langage old:" + oldLanguage);
				setLanguage(oldLanguage);
			}

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
		
		
		oldLanguage = getLanguage();
		
		Cookie cookie = new Cookie("language", oldLanguage);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60*60*24*30*12);
		cookie.setDomain("localhost");
		
		//FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("ManCookie", oldLanguage, null);
		HttpServletResponse resp = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		resp.addCookie(cookie);
		
		return retVal;
	}
	
	public String register(){
		String retVal = "guest?faces-redirect=true";
		if(LoginDAO.register(username, password, email)){
			retVal = login();
		}
		
		return retVal;
	}
	
	public String getLanguage(){
		return locale.getLanguage();
	}
	
	public void setLanguage(String language){
		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Map<String, String> getAvailableItems() {
		return availableItems;
	}

	public void setAvailableItems(Map<String, String> availableItems) {
		this.availableItems = availableItems;
	}

	
	
	
	
}
