package bean;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.rpc.ServiceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actor.Actor;
import actor.ActorServiceLocator;
import dao.GenreDAO;
import dao.MovieDAO;
import dao.MovieHasActorDAO;
import dao.MovieHasGenreDAO;
import dto.ActorDTO;
import dto.GenreDTO;
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
	
	//Selected actors
	//List<ActorDTO> selectedActors;
	//from input field on form
	String actorName;
	//all actors from Soap
	List<String> actors;
	//used in JavaScript on form
	String actorsString;
	
	//from input field on form
	String genresStringFromInput;
	//used in JavaScript on form
	String genresStringToShowOnForm;
	
	Integer[] selectedGenres;
	Map<Integer, String> genreValues;
	
	private static final long serialVersionUID = -6851375545924053833L;

	public MovieBean() {
		keyWord = null;
		foundMoviesList = null;
		movieSelected = null;
		movieInsert = new MovieDTO();
		//selectedActors = new ArrayList<>();
		
	}

	@PostConstruct
	public void init(){
		setActorsString(getActorsString());

		genreValues = new LinkedHashMap<>();
		List<GenreDTO> gens = GenreDAO.getAll();
		for (GenreDTO g : gens) {
			//System.out.print(g.getName());
			genreValues.put(g.getId(), g.getName().toString());
		}
		
	}
	
	
	public String search(){
		foundMoviesList = MovieDAO.search(keyWord);
		
		//return "guest.xhtml?faces-redirect=true";
		return null;
	}
	
	public String details(){
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String movie = params.get("selectedMovie");
		movieSelected = MovieDAO.getByTitle(movie);
		System.out.println("Film: " + movieSelected.getTitle());
		
		return "movie?faces-redirect=true";
	}
	
	public String details2(MovieDTO movie){
		movieSelected = movie;
		
		//return "pages/movie.xhtml?faces-redirect=true";
		return "movie.xhtml?faces-redirect=true";
	}
	
	public void addMovie(){
		try {
			
			//array of actors out of string from form
			String[] actors = actorName.split(",");
			//clear actors string for the subsequent use
			actorName = "";
			
			//insert all actors who haven't existed in database through Soap ws
			//Soap
			Actor a = new ActorServiceLocator().getActor();
			for(String actor : actors){
				int actorId = a.insertActor(actor);
//				if (actorId > 0)
//					System.out.println("Dodat actor " + actor + ": " + actorId);
//				else
//					System.out.println("Actor " + actor +  " nije dodat");
			}			
			
			//add movie to database
			int movieId = MovieDAO.insert(movieInsert);
			if (movieId > 0){
				System.out.println("Successful add movie " + movieInsert.getTitle());

				//add relations between movie and gneres
				//form creates array with first null element if "select" is chosen
				if(selectedGenres != null && selectedGenres[0] != null)
					for(Integer genreId : selectedGenres)
						MovieHasGenreDAO.insert(movieId, genreId);
				//clear genre property for the subsequent use
				selectedGenres = null;
				
				//add relations between movie and actors
				Map<String, ActorDTO> mapOfAllActors = MovieDAO.getAllActorsNameMap();
				for(String actor : actors){
					int movieHasActorRowCount = MovieHasActorDAO.insert(movieId, mapOfAllActors.get(actor).getId());
//					if (movieHasActorRowCount > 0)
//						System.out.println("Successful save relation actor " + actor + " to movie " + movieInsert.getTitle());
//					else
//						System.out.println("Unsuccessful save relation actor " + actor + " to movie " + movieInsert.getTitle());
				}
			} else
				System.out.println("Not added movie " + movieInsert.getTitle());

			//clear movie property for the subsequent use
			movieInsert = new MovieDTO();

			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
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
		List<String> actors = new ArrayList<>();
		//Soap
		Actor a;
		
		try {
			a = new ActorServiceLocator().getActor();

			JSONArray jArray = new JSONArray(a.getActors(null));
			//System.out.println("received jArray: " + jArray.toString());
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
//		String retVal = "";
		List<String> a = getActors();
		JSONArray jArr = new JSONArray(a);

		//System.out.println("jArr: " + jArr.toString());
//		if(retVal.length() > 0)
//			actorsString = retVal.substring(0, retVal.length() - 1);
		actorsString = jArr.toString();
		//System.out.println("actorString: " + actorsString);
		return actorsString;
	}

	public void setActorsString(String actorsString) {
		this.actorsString = actorsString;
	}

	public String getGenresStringFromInput() {
		return genresStringFromInput;
	}

	public void setGenresStringFromInput(String genersStringFromInput) {
		this.genresStringFromInput = genersStringFromInput;
	}

	public String getGenresStringToShowOnForm() {
		List<GenreDTO> genreDTOList = GenreDAO.getAll();
		List<String> stringList = new ArrayList<>();
		for(GenreDTO g : genreDTOList)
			stringList.add(g.getName());
		JSONArray jArr = new JSONArray(stringList);
		genresStringToShowOnForm = jArr.toString();
		return genresStringToShowOnForm;
	}

	public void setGenresStringToShowOnForm(String genersStringToShowOnForm) {
		this.genresStringToShowOnForm = genersStringToShowOnForm;
	}

	public Integer[] getSelectedGenres() {
		return selectedGenres;
	}

	public void setSelectedGenres(Integer[] selectedGenres) {
		this.selectedGenres = selectedGenres;
	}

	public Map<Integer, String> getGenreValues() {
		return genreValues;
	}

	public void setGenreValues(Map<Integer, String> genreValues) {
		this.genreValues = genreValues;
	}




	
	
	
	
}
