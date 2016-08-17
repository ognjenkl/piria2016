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
		
		if(user != null)
			System.out.println(user.toString());
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
	
	public UserDTO getUser() {
		if(user != null){
			System.out.println("get: " + user.toString());
			System.out.println("get login.user: " + loginBean.getUser().toString());
		}
		return user;
	}

	public void setUser(UserDTO user) {
		if(user != null){
			System.out.println("set: " + user.toString());
			System.out.println("set login.user: " + loginBean.getUser().toString());

		}
		this.user = user;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	
	

}
