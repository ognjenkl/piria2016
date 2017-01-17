package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.FavoriteMovieListDAO;
import dao.FavoriteMovieListHasMovieDAO;
import dao.MovieDAO;
import dto.FavoriteListDTO;
import dto.MovieDTO;
import util.JSFUtil;

@ManagedBean (name="favorite")
@ViewScoped
public class FavoriteListBean {

	private FavoriteListDTO favoriteListToAdd;
	private List<FavoriteListDTO> favoriteLists;
	private Integer userId;
	private List<MovieDTO> allMoviesList;
	private Integer favoriteMovieIdToAdd;
	

	
	@PostConstruct
	public void init() {
		System.out.println("init");
		favoriteListToAdd = new FavoriteListDTO();
		userId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("userId");
		System.out.println("init userId: " + userId);
		
		if(userId != null){
			favoriteLists = FavoriteMovieListDAO.getAllByUserId(userId);
			allMoviesList = MovieDAO.getAll(); 
		}

	}
	
	
	
	public String favoritePage(Integer userId) {
		System.out.println("method favorite page: " + userId);
//		if(userId != null)
//			favoriteLists = FavoriteMovieListDAO.getAllByUserId(userId);

		this.userId = userId;
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("userId", userId);
		
		System.out.println("favorite page return: " + userId);
		return "favoriteLists?faces-redirect=true";
	}
	
	
	
	public String addFavoriteMovieList() {
		if(favoriteListToAdd != null && !favoriteListToAdd.getName().equals("")) {
			if(FavoriteMovieListDAO.insert(favoriteListToAdd.getName(), userId) > 0 )
				FacesContext.getCurrentInstance().addMessage("favoriteListAddForm", new FacesMessage(JSFUtil.getLangMessage("favoriteAddListSuccessful")));
			else
				FacesContext.getCurrentInstance().addMessage("favoriteListAddForm", new FacesMessage(JSFUtil.getLangMessage("favoriteAddListUnsuccessful")));
			
		} else
			FacesContext.getCurrentInstance().addMessage("favoriteListAddForm", new FacesMessage(JSFUtil.getLangMessage("favoriteAddListDoesntExist")));

		favoriteLists.add(favoriteListToAdd);
		favoriteListToAdd = new FavoriteListDTO();
		return null;
	}
	
	
	
	public String addFavoriteMovieToList(FavoriteListDTO favoriteListDTO) {
		System.out.println("add movie favorite list name: " + favoriteListDTO.getName());
		System.out.println("add movie id: " + favoriteMovieIdToAdd);
		Integer resultInsert = FavoriteMovieListHasMovieDAO.insert(favoriteListDTO.getId(), favoriteListDTO.getUsersId(), favoriteMovieIdToAdd);
		
		if ( resultInsert != null && resultInsert > 0 )
			FacesContext.getCurrentInstance().addMessage("favoriteMovieAddForm", new FacesMessage(JSFUtil.getLangMessage("favoriteAddMovieSuccessful")));
		else
			FacesContext.getCurrentInstance().addMessage("favoriteMovieAddForm", new FacesMessage(JSFUtil.getLangMessage("favoriteAddMovieUnsuccessful")));
		
		favoriteMovieIdToAdd = null;
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

	public List<MovieDTO> getAllMoviesList() {
		return allMoviesList;
	}

	public void setAllMoviesList(List<MovieDTO> allMoviesList) {
		this.allMoviesList = allMoviesList;
	}

	public Integer getFavoriteMovieIdToAdd() {
		return favoriteMovieIdToAdd;
	}

	public void setFavoriteMovieIdToAdd(Integer favoriteMovieIdToAdd) {
		this.favoriteMovieIdToAdd = favoriteMovieIdToAdd;
	}
	
}
