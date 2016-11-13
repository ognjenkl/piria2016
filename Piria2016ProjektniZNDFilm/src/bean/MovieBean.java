package bean;

import java.awt.JobAttributes;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.rpc.ServiceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actor.Actor;
import actor.ActorServiceLocator;
import dao.MovieDAO;
import dto.ActorDTO;
import dto.MovieDTO;

/**
 * @author ognjen
 *
 */
@ManagedBean (name="movie")
@SessionScoped
public class MovieBean implements Serializable{
	
	String keyWord;
	//lista filmova trazenih u pretrazi
	List<MovieDTO> foundMoviesList;
	//film koji ce biti prikazan na stranici movie.xhtml zasebno
	MovieDTO movieSelected;
	//movie to be inserted ie. added
	MovieDTO movieInsert;
	//All actors in db
	//Map<Integer, ActorDTO> actors;
	List<String> actors;
	String actorsString;
	
	

	//Selected actors
	List<ActorDTO> selectedActors;
	String actorName;
	
	private static final long serialVersionUID = -6851375545924053833L;

	public MovieBean() {
		keyWord = null;
		foundMoviesList = null;
		movieSelected = null;
		movieInsert = new MovieDTO();
		selectedActors = new ArrayList<>();
	}

	public String search(){
		foundMoviesList = MovieDAO.search(keyWord);
		
		//return "guest.xhtml?faces-redirect=true";
		return null;
	}
	
	public String details(){
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String movie = params.get("selectedMovie");
		movieSelected = MovieDAO.getMovie(movie);
		System.out.println("Film: " + movieSelected.getTitle());
		
		return "movie?faces-redirect=true";
	}
	
	public String details2(MovieDTO movie){
		movieSelected = movie;
		
		//return "pages/movie.xhtml?faces-redirect=true";
		return "movie.xhtml?faces-redirect=true";
	}
	
	public void addMovie(){
		MovieDAO.insertMovie(movieInsert);
		movieInsert = null;
	}

	
	
	public void addActor(){
		try {
			//Actor a = new ActorProxy();
			System.out.println("actor name: " + actorName);
			Actor a = new ActorServiceLocator().getActor();
		
			//a.addActor(actorName);

//			List<ActorDTO> listOfActors = new ArrayList<>();
//			for(Object o : objs)
//				listOfActors.add((ActorDTO) o);
			
				
//			for(int i = 0; i < listOfActors.size(); i++)
//				System.out.println(i + ". " + listOfActors.get(i).getName());
		
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public MovieDTO getMovieSelected() {
		return movieSelected;
	}

	public void setMovieSelected(MovieDTO movieSelected) {
		this.movieSelected = movieSelected;
	}

	public MovieDTO getMovieInsert() {
		return movieInsert;
	}

	public void setMovieInsert(MovieDTO movieInsert) {
		this.movieInsert = movieInsert;
	}

	public List<ActorDTO> getSelectedActors() {
		return selectedActors;
	}

	public void setSelectedActors(List<ActorDTO> selectedActors) {
		this.selectedActors = selectedActors;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	/**
	 * Get actors from SOAP WS Actor
	 * @return
	 */
	public List<String> getActors(){
		//Map<Integer, ActorDTO> actors = MovieDAO.getAllActorsMap();
		//System.out.println(actors.get(1).getName());
		
		List<String> actors = new ArrayList<>();
		Actor a;
		
		try {
			
			a = new ActorServiceLocator().getActor();

			JSONArray jArray = new JSONArray(a.getActors(actorName));
			System.out.println("received jArray: " + jArray.toString());
			for(int i = 0; i < jArray.length(); i++){
				JSONObject jObj = jArray.getJSONObject(i);
				actors.add(jObj.getString("name"));
			}		
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		return actors;
	}
		
	public void setActors(List<String> actors) {
		this.actors = actors;
	}


	public String getActorsString() {
		String retVal = "";
		List<String> a = getActors();
		JSONArray jArr = new JSONArray(a);
		//JSONObject jObj = new JSONObject(a);
//		for(String aS : a){
//			try {
//				jObj.pu;
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//retVal += "\"" + aS + "\"" + ",";
//			
//		}

		System.out.println("jArr: " + jArr.toString());
		if(retVal.length() > 0)
			actorsString = retVal.substring(0, retVal.length() - 1);
		actorsString = jArr.toString();
		System.out.println("actorString: " + actorsString);
		return actorsString;
	}

	public void setActorsString(String actorsString) {
		this.actorsString = actorsString;
	}
	
	
	
	
}
