package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.MovieDAO;
import dto.MovieDTO;

/**
 * @author ognjen
 *
 */
@ManagedBean (name="movie")
@ViewScoped
public class MovieBean implements Serializable{
	
	String keyWord;
	List<MovieDTO> foundMoviesList;
	
	private static final long serialVersionUID = -6851375545924053833L;

	public MovieBean() {
		keyWord = null;
		foundMoviesList = null;
	}

	public String search(){
		foundMoviesList = MovieDAO.search(keyWord);
		
		//return "guest.xhtml?faces-redirect=true";
		return null;
	}
	
	
	
	

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public List<MovieDTO> getFoundMoviesList() {
		return foundMoviesList;
	}

	public void setFoundMoviesList(List<MovieDTO> foundMoviesList) {
		this.foundMoviesList = foundMoviesList;
	}

	
	
	
	
	
	
}
