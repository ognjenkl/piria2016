package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dao.UserDAO;
import dto.UserDTO;

/**
 * @author ognjen
 *
 */
@ViewScoped
@ManagedBean(name="manageAccounts")
public class ManageAccountsBean {

	@ManagedProperty(value="#{login}")
	LoginBean loginBean;
	
	List<UserDTO> usersList;
	
	public ManageAccountsBean() {
	}

	@PostConstruct
	public void init(){
		usersList = UserDAO.getAll();
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public List<UserDTO> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<UserDTO> usersList) {
		this.usersList = usersList;
	}
	
	
}
