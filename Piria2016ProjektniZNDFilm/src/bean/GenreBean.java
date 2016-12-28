package bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import dao.GenreDAO;
import dto.GenreDTO;
import util.JSFUtil;

/**
 * @author ognjen
 *
 */
@ManagedBean (name="genre")
@RequestScoped
public class GenreBean implements Serializable{
	
	private static final long serialVersionUID = -3470442255313603101L;
	GenreDTO genre;	
		
	public GenreBean() {
		genre = new GenreDTO();
	}
	
	public void addGenre() {
		int inserted = GenreDAO.insert(genre);
		if (inserted > 0){
			genre = new GenreDTO();
			FacesContext.getCurrentInstance().addMessage( "genreForm", new FacesMessage(JSFUtil.getLangMessage("genreAddSuccesful")));
		} else {
			FacesContext.getCurrentInstance().addMessage("genreForm", new FacesMessage(JSFUtil.getLangMessage("genreAddError")));
		}
	}

	
	
	
	public GenreDTO getGenre() {
		return genre;
	}

	public void setGenre(GenreDTO genre) {
		this.genre = genre;
	}
	
	
	
}
