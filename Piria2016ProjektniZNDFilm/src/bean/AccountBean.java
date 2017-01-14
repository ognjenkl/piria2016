package bean;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import dto.UserDTO;
import util.JSFUtil;

/**
 * @author ognjen
 *
 */
@ViewScoped
@ManagedBean(name="account")
public class AccountBean {

	@ManagedProperty(value="#{login}")
	LoginBean loginBean;
	
	UserDTO user;
	
	Part profilePicPart;
	
	/**
	 * Used for account editing properties. 
	 */
	public AccountBean() {

	}

	@PostConstruct
	public void init(){
		if(loginBean != null){
			user = new UserDTO(loginBean.getUser().getId(), loginBean.getUser().getFirstName(), loginBean.getUser().getLastName(), loginBean.getUser().getSocialNo(), loginBean.getUser().getEmail(), loginBean.getUser().getPicture(), loginBean.getUser().getUsername(), loginBean.getUser().getPassword(), loginBean.getUser().getPrivilege(), loginBean.getUser().isActive(), loginBean.getUser().isEditable());
		}
	}
	
	public String accountSave(){
		Properties prop = new Properties();
		try {
			prop.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/config/config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String filePath = JSFUtil.uploadProfilePic(user.getUsername(), profilePicPart, prop);
		user.setPicture(filePath);
		
		if(!user.getPassword().equals(""))
			if (loginBean.updateUserWithoutPrivilege(user))
				FacesContext.getCurrentInstance().addMessage( "formAccount", new FacesMessage(JSFUtil.getLangMessage("saveAccountSuccessfulMessage")));
			else
				FacesContext.getCurrentInstance().addMessage( "formAccount", new FacesMessage(JSFUtil.getLangMessage("saveAccountErrorMessage")));
			
		else 
			if (loginBean.updateUserWithoutPasswordAndPrivilege(user))
				FacesContext.getCurrentInstance().addMessage( "formAccount", new FacesMessage(JSFUtil.getLangMessage("saveAccountSuccessfulMessage")));
			else
				FacesContext.getCurrentInstance().addMessage( "formAccount", new FacesMessage(JSFUtil.getLangMessage("saveAccountErrorMessage")));
			

		return null;
	}
	
	public String accountSave(UserDTO user){
		user.setEditable(false);
		this.user = user;
		loginBean.updateUser(user);
		
		return null;
	}
	
	public String accoountEdit(UserDTO user){
		user.setEditable(true);
		this.user = user;
		
		return null;
	}
				  
	public String accountActivate(UserDTO user){
		user.setActive(true);
		return null;
	}

	public String accountDeactivate(UserDTO user){
		user.setActive(false);
		return null;
	}
	
	
	
	
	
	
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Part getProfilePicPart() {
		return profilePicPart;
	}

	public void setProfilePicPart(Part profilePicPart) {
		this.profilePicPart = profilePicPart;
	}
	
	
	

}
