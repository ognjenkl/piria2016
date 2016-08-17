package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dto.UserDTO;

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
		System.out.println("constructor");
	}

	@PostConstruct
	public void init(){
		if(loginBean != null){
			user = new UserDTO(loginBean.getUser().getFirstName(), loginBean.getUser().getLastName(), loginBean.getUser().getSocialNo(), loginBean.getUser().getEmail(), loginBean.getUser().getPicture(), loginBean.getUser().getUsername(), loginBean.getUser().getPassword(), loginBean.getUser().getPrivilege());
			System.out.println("init: " + user.toString());
		}
	}
	
	public String accountSave(){
		if(!user.getPassword().equals(""))
			loginBean.updateUserWithoutPrivilege(user);
		else
			loginBean.updateUserWithoutPasswordAndPrivilege(user);
		
		return null;
	}
	
	public String accountSave(UserDTO user){
		System.out.println("save account: " + this.user.getUsername());
		user.setEditable(false);
		this.user = user;
		
//		return "manageAccounts?faces-redirect=true";
		return null;
	}
	
	public String editAccount(UserDTO user){
		System.out.println("edit account: " + this.user.getUsername());
		user.setEditable(true);
		this.user = user;
		
//		return "manageAccounts?faces-redirect=true";
		return null;
	}
	
	public String editSelf(){
		System.out.println("edit self");		
		user = new UserDTO(loginBean.getUser().getFirstName(), loginBean.getUser().getLastName(), loginBean.getUser().getSocialNo(), loginBean.getUser().getEmail(), loginBean.getUser().getPicture(), loginBean.getUser().getUsername(), loginBean.getUser().getPassword(), loginBean.getUser().getPrivilege());
		return "account?faces-redirect=true";
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
