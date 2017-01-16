package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.FavoriteMovieListDAO;
import dto.FavoriteListDTO;

@ManagedBean (name="favorite")
@ViewScoped
public class FavoriteListBean {

	private FavoriteListDTO favoriteListToAdd;
	private List<FavoriteListDTO> favoriteLists;
	private Integer userId;
	
	@PostConstruct
	public void init() {
		favoriteListToAdd = new FavoriteListDTO();
		userId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("userId");
		favoriteLists = new ArrayList<>();
	}
	
	public String favoritePage(Integer userId) { 
		this.userId = userId;
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("userId", userId);
		return "favoriteLists?faces-redirect=true";
	}
	
	public String addFavoriteMovieList() {
		FavoriteMovieListDAO.insert(favoriteListToAdd.getName(), userId);
		
		return null;
	}
	
	
	
	public FavoriteListDTO getFavoriteListToAdd() {
		return favoriteListToAdd;
	}
	public void setFavoriteListToAdd(FavoriteListDTO favoriteListToAdd) {
		this.favoriteListToAdd = favoriteListToAdd;
	}
	public List<FavoriteListDTO> getFavoriteLists() {
		return favoriteLists;
	}
	public void setFavoriteLists(List<FavoriteListDTO> favoriteLists) {
		this.favoriteLists = favoriteLists;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	
	
	
}
