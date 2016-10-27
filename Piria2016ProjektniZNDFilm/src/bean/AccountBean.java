package bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
	
	
	

}
