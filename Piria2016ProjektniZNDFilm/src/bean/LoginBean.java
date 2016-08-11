package bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
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
	
	//user if logged in
	UserDTO user;
	//set properties during registration process
	UserDTO userRegister;
	
	String username;
	String password;
	
	boolean loggedIn;
	
	//localization
	Locale locale;
	Map<String, String> availableItems;
	String language;
	
	//config properties
	Properties prop;
	
	//movie bean
	@ManagedProperty(value="#{movie}")
	MovieBean movie;
	
	
	
	
	
	//consturctor
	public LoginBean(){
		username = "";
		password = "";
		loggedIn = false;
		user = new UserDTO();
		userRegister = new UserDTO();
		availableItems = new TreeMap<>();
		locale = null;
		
		prop = new Properties();
		try {
			prop.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/config/config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@PostConstruct
	public void init(){
		String languageFromCookie = readLanguageFromCookie();
		if(languageFromCookie != null){
			setLanguage(languageFromCookie);
		}
		else{
			locale =  FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
			//setLanguage("sr");
			setLanguage(prop.getProperty("langShortSr"));

		}
		
		availableItems.put(prop.getProperty("langShortEn"),prop.getProperty("langLongEn"));
		availableItems.put(prop.getProperty("langShortSr"),prop.getProperty("langLongSr"));
		availableItems.put(prop.getProperty("langShortFr"),prop.getProperty("langLongFr"));
		availableItems.put(prop.getProperty("langShortDe"),prop.getProperty("langLongDe"));

		
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

		
		return retVal;
	}
	
	public String logout(){
		String retVal = "guest?faces-redirect=true";
		
		((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		loggedIn = false;
		//reset user to guest
		user = new UserDTO();
		
		return retVal;
	}
	
	public String register(){
		String retVal = "guest?faces-redirect=true";
		if(LoginDAO.register(userRegister)){
			retVal = login();
		}
		
		userRegister = null;
		
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
		Cookie c = (Cookie)map.get(prop.getProperty("cookieLangName"));
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
			propertiesCookie.put("domain", prop.getProperty("cookieLangDomain"));
			propertiesCookie.put("path", prop.getProperty("cookieLangPath"));
			propertiesCookie.put("httpOnly", true);
			propertiesCookie.put("maxAge", 60*60*24*30*12);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.addResponseCookie(
					prop.getProperty("cookieLangName"),
					URLEncoder.encode(language, "UTF-8"), 
					propertiesCookie);
			System.out.println("create cookie: " + prop.getProperty("cookieLangName") + " " + language);

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

	public UserDTO getUserRegister() {
		return userRegister;
	}

	public void setUserRegister(UserDTO userRegister) {
		this.userRegister = userRegister;
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

	public MovieBean getMovie() {
		return movie;
	}

	public void setMovie(MovieBean movie) {
		this.movie = movie;
	}
	
	
	
	
}
