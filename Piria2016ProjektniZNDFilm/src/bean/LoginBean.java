package bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.spi.ResolveResult;
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
	
	//Localization messages
	@ManagedProperty("#{msg}")
	ResourceBundle msgResourceBundle;
	
	
	
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
	
	/*
	 * Login with loginBean's properties username i password, which are afterwards set to empty
	 * 
	 */
	public void login(){
		UserDTO loggedUser = LoginDAO.login(username, password);
		if(loggedUser != null){
			user = loggedUser;
			loggedIn = true;
		} else {
			loggedIn = false;
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String messageBundle = facesContext.getApplication().getMessageBundle();
			System.out.println("mess bundle: " + messageBundle);
			Locale locale = facesContext.getViewRoot().getLocale();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			ResourceBundle msgResourceBundle = ResourceBundle.getBundle("resources.lang", locale, loader);
			
			//FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage("Neuspjesan login"));
			FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage(msgResourceBundle.getString("loginErrorMessage")));
		}
		//username = "";
		//password = "";


	}
	
	/*
	 * Login with userRegister properties, which is afterwards set to default UserDTO object
	 * 
	 */
	public void login(UserDTO userReg){
		UserDTO loggedUser = LoginDAO.login(userReg.getUsername(), userReg.getPassword());
		if(loggedUser != null){
			user = loggedUser;
			userReg = new UserDTO();
			loggedIn = true;
		} else
			loggedIn = false;
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
		if(LoginDAO.register(userRegister)){
			login(userRegister);
		}
		
		return null;
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

	public ResourceBundle getMsgResourceBundle() {
		return msgResourceBundle;
	}

	public void setMsgResourceBundle(ResourceBundle msgResourceBundle) {
		this.msgResourceBundle = msgResourceBundle;
	}
	
	
	
	
}
