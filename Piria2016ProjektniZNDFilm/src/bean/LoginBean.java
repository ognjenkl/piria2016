package bean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
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
	
	//localization
	Locale locale;
	Map<String, String> availableItems;
	String language;
	
	public LoginBean(){
		username = "";
		password = "";
		email = "";
		loggedIn = false;
		user = null;
		availableItems = new TreeMap<>();
		locale = null;
		
		
	}

	@PostConstruct
	public void init(){
		System.out.println("init pocetak:");
		String langu = readLanguageFromCookie();
		if(langu != null){
			setLanguage(langu);
			System.out.println("init if langu: " + langu);
		}
		else{
			locale =  FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
			setLanguage("sr");
			System.out.println("init locale else: ");

		}
		System.out.println("init :" + locale.getLanguage());
		
		availableItems.put("en","English");
		availableItems.put("sr", "Srpski");
		availableItems.put("fr", "Francais");
		availableItems.put("de", "Deutsche");
		
	}
	
	public String login(){
		UserDTO loggedUser = null;
		String retVal = null;

		
		loggedUser = LoginDAO.login(username, password);
		if(loggedUser != null){
			user = loggedUser;
			loggedIn = true;
			retVal = null;
		} else {
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
	
	public String getLanguage(){
		return locale.getLanguage();
	}
	
	public void setLanguage(String language){
		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		createCookieLang(language);
	}

	
	/**
	 * Reads language cookie
	 * 
	 * @return
	 */
	public String readLanguageFromCookie(){
		Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
		Cookie c = (Cookie)map.get("language");
		try {
			if(c != null){
				System.out.println("coookieeee " + URLDecoder.decode(c.getValue(), "UTF-8"));
				return URLDecoder.decode(c.getValue(), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Creates language cookie.
	 * 
	 * @param language
	 */
	public void createCookieLang(String language){
		try {

			Map<String, Object> propertiesCookie = new Hashtable<>();
			propertiesCookie.put("secure", false);
			propertiesCookie.put("domain", ".localhost");
			propertiesCookie.put("path", "/");
			propertiesCookie.put("httpOnly", true);
			propertiesCookie.put("maxAge", 60*60*24*30*12);
			
			FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(
					"language", 
					URLEncoder.encode(language, "UTF-8"), 
					propertiesCookie);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
