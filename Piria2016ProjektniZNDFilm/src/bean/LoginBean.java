package bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.UserDAO;
import dto.UserDTO;
import util.JSFUtil;

/**
 * @author ognjen
 *
 */
@SessionScoped
@ManagedBean (name="login", eager=true)
public class LoginBean {
	
	
	//login: user is set if logged in
	UserDTO user;

	//prelogin
	String username;
	String password;
	boolean loggedIn;

	//registration: set properties during registration process
	UserDTO userRegister;
	
	
	//localization
	Locale locale;
	Map<String, String> availableItems;
	String language;
	
	//Localization messages
	@ManagedProperty("#{msg}")
	ResourceBundle msgResourceBundle;
	
	//config properties
	Properties prop;
	
	//movie bean
//	@ManagedProperty(value="#{movie}")
//	MovieBean movie;
	
	//admin
	List<UserDTO> usersAll;
	
	Part profilePicPart;
	String defaultProfilePic;
	
	
	
	//consturctor
	public LoginBean(){
		username = "";
		password = "";
		loggedIn = false;
		user = new UserDTO();
		userRegister = new UserDTO();
		availableItems = new TreeMap<>();
		locale = null;
		
		//admin
		usersAll = null;
		
		prop = new Properties();
		try {
			prop.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/config/config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		defaultProfilePic = prop.getProperty("upload.profile.default.name");
		
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
	public String login(){
		UserDTO loggedUser = UserDAO.login(username, password);
		if(loggedUser != null){
			user = loggedUser;
			loggedIn = true;
		} else {
			loggedIn = false;
			FacesContext.getCurrentInstance().addMessage( "loginForm", new FacesMessage(JSFUtil.getLangMessage("loginErrorMessage")));
		}
		
		return null;
	}
	
//	/*
//	 * Login with userRegister properties, which is afterwards set to default UserDTO object
//	 * 
//	 */
//	public void login(UserDTO userReg){
//		UserDTO loggedUser = UserDAO.login(userReg.getUsername(), userReg.getPassword());
//		if(loggedUser != null){
//			user = loggedUser;
//			userReg = new UserDTO();
//			loggedIn = true;
//		} else
//			loggedIn = false;
//	}
//	
	public String logout(){
		String retVal = "guest?faces-redirect=true";
		
		((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		loggedIn = false;
		//reset user to guest
		user = new UserDTO();
		
		return retVal;
	}
	
	public String register(){
		if(UserDAO.getByUsername(userRegister.getUsername()) == null){
			String filePath = uploadProfilePic(userRegister.getUsername());
			if( filePath != null) {
				userRegister.setPicture(filePath);
				if(UserDAO.insert(userRegister)){
					userRegister = new UserDTO();
					FacesContext.getCurrentInstance().addMessage("registerForm", new FacesMessage(JSFUtil.getLangMessage("registerSuccessful")));
				} else
					FacesContext.getCurrentInstance().addMessage("registerForm", new FacesMessage(JSFUtil.getLangMessage("registerUnsuccessful")));				
			} else
				FacesContext.getCurrentInstance().addMessage("registerForm", new FacesMessage(JSFUtil.getLangMessage("registerProfilePic")));				
		} else
			FacesContext.getCurrentInstance().addMessage("registerForm", new FacesMessage(JSFUtil.getLangMessage("registerAlreadyExist")));
		
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
	
	/*
	 * Admin accounts update, not for self-account update use.
	 */
	public boolean updateUser(UserDTO user){
		return UserDAO.updateUser(user);
		//this.user = user;
	}
	
	public boolean updateUserWithoutPasswordAndPrivilege(UserDTO user){
		if(UserDAO.updateUserWithoutPasswordAndPrivilege(user)){
			this.user = UserDAO.getByUsername(user.getUsername());
			return true;
		}
		
		return false;
	}
	
	public boolean updateUserWithoutPrivilege(UserDTO user){
		if(UserDAO.updateUserWithoutPrivilege(user)){
			this.user = UserDAO.getByUsername(user.getUsername());
			return true;
		}
		
		return false;
	}	
	
	/*
	 * Returns string path of the uploaded profile picture.
	 */
	/**
	 * Uploads profile picture on server.
	 * @param profilePicName name of the profile picture to be saved as.
	 * @return profile picture name with extension.
	 */
	public String uploadProfilePic(String profilePicName) {
		try(InputStream in = profilePicPart.getInputStream()) {
			String dirPath = prop.getProperty("upload.profile.location");
			File dir = new File(dirPath);
			if(dir.exists()) {
				String fileName = profilePicName + ".png";
				//if no profile pic is specified
				if(JSFUtil.getFilename(profilePicPart).equals("")){
					fileName = defaultProfilePic;
					System.out.println("naziv profilne slike: " + fileName);
					return fileName;
				}
				
				System.out.println("naziv profilne slike: " + fileName);
				if(fileName.endsWith(".jpg") || fileName.endsWith(".png")
						|| fileName.endsWith(".JPG") || fileName.endsWith(".PNG")) {
					String filePath = dirPath + File.separator + fileName;
					File f = new File(filePath);
					if (!f.exists()) {
						Files.copy(in, new File(filePath).toPath());
						System.out.println("Uploaded profile file: " + filePath);
						return fileName;
					} else {
						System.out.println("Upload file \"" + fileName + "\" already exists");
						return fileName;
					}
				} else {
					System.out.println("Wrong upload file format!");
					return null;
				}
			} else {
				System.out.println("Directory \"" + dirPath + "\" for upload does not exist");
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Something is wrong with moviePart: ");
			System.out.println(profilePicName);
			return null;
		}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////// getters and setters /////////////////////////////
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

//	public MovieBean getMovie() {
//		return movie;
//	}
//
//	public void setMovie(MovieBean movie) {
//		this.movie = movie;
//	}

	public ResourceBundle getMsgResourceBundle() {
		return msgResourceBundle;
	}

	public void setMsgResourceBundle(ResourceBundle msgResourceBundle) {
		this.msgResourceBundle = msgResourceBundle;
	}

	public List<UserDTO> getUsersAll() {
		if(user.getPrivilege() < 2)
			usersAll = UserDAO.getAll();
		else{
			usersAll = new ArrayList<UserDTO>();
			usersAll.add(user);
		}
		return usersAll;
	}

	public void setUsersAll(List<UserDTO> usersAll) {
		this.usersAll = usersAll;
	}

	public Part getProfilePicPart() {
		return profilePicPart;
	}

	public void setProfilePicPart(Part profilePicPart) {
		this.profilePicPart = profilePicPart;
	}

	
	
	
	
}
