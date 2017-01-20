package bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.xml.rpc.ServiceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.primefaces.event.RateEvent;

import actor.Actor;
import actor.ActorServiceLocator;
import dao.ActorDAO;
import dao.GenreDAO;
import dao.MovieDAO;
import dao.MovieHasActorDAO;
import dao.MovieHasGenreDAO;
import dao.UserHasMovieCommentDAO;
import dao.UserHasMovieDAO;
import dto.ActorDTO;
import dto.CommentDTO;
import dto.GenreDTO;
import dto.MovieDTO;
import dto.UserHasMovieDTO;
import util.JSFUtil;

/**
 * @author ognjen
 *
 */
@ManagedBean (name="movie")
//@SessionScoped //zbog login bean-a, ne moze biti manji skope nego u login
@ViewScoped
//@RequestScoped
public class MovieBean implements Serializable{
	
	private static final long serialVersionUID = -6851375545924053833L;
	
	//search
	String keyWord;
	//lista filmova trazenih u pretrazi
	//koristi se       	<Context docBase="G:\filmUpload" path="/movies/" /> na Tomcat serveru u server.xml
	List<MovieDTO> foundMoviesList;
	//film koji ce biti prikazan na stranici movie.xhtml zasebno
	MovieDTO movieSelected;
	String[] searchCriteriaCheckBoxes;
	Map<String, String> searchCriteriaPredefinedValues;
	Map<Integer, MovieDTO> foundMoviesMap;
	
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
//	String genresStringFromInput;
	//used in JavaScript on form
//	String genresStringToShowOnForm;
	
	Integer[] selectedGenres;
	Map<Integer, String> genreValues;
	
	Part moviePart;
	
	Properties prop;
	
	//for movie edit
	boolean editable;
	
	MovieDTO movieEdit;
	
	List<String> actorsToDeleteOnSaveList;
	
	List<GenreDTO> genresAllList;
	
	Double movieRate;
	
	Integer userId;
	
	UserHasMovieDTO userHasMovie;
	
	List<CommentDTO> commentsList;
	String comment;
	
	
	
	
	public MovieBean() {
//		keyWord = null;
//		foundMoviesList = null;
//		movieSelected = null;
		movieInsert = new MovieDTO();
		
		
		prop = new Properties();
		try {
			prop.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/config/config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		editable = false;
		movieEdit = new MovieDTO();
		actorsToDeleteOnSaveList = new ArrayList<>();
		
		searchCriteriaPredefinedValues = new HashMap<>();
		searchCriteriaPredefinedValues.put("Title", "title");
		searchCriteriaPredefinedValues.put("Genre", "genre");
		searchCriteriaPredefinedValues.put("Actor", "actor");
		
	}

	@PostConstruct
	public void init(){
		genreValues = new LinkedHashMap<>();
		List<GenreDTO> gens = GenreDAO.getAll();
		for (GenreDTO g : gens) 
			genreValues.put(g.getId(), g.getName().toString());
		
		userId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("userId");

		movieSelected = (MovieDTO) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("movie");
		if(movieSelected != null) {
			selectedGenres = MovieHasGenreDAO.getGenreIdListByMovieId(movieSelected.getId());
			userHasMovie = UserHasMovieDAO.getById(userId, movieSelected.getId());
			if(userHasMovie == null) 
				userHasMovie = new UserHasMovieDTO();
			
			commentsList = UserHasMovieCommentDAO.getByMovieId(movieSelected.getId());

		}
		movieEdit = (MovieDTO) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("movieEdit");

		Object e = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("editable");
		if( e != null)
			editable = (boolean) e;
		
		setActorsString(getActorsString());
		
		genresAllList = GenreDAO.getAll();

		
	}
	
	
	public String search(){
		//foundMoviesList = MovieDAO.getAllByTitleLike(keyWord);
		//foundMoviesList = new ArrayList<>();
		foundMoviesMap = new HashMap<>();
		if(keyWord != null && !keyWord.equals(""))
			for(String c : searchCriteriaCheckBoxes){
				switch (c) {
					case "title":
						System.out.println("Checked: " + c);
						//for(MovieDTO m : MovieDAO.getAllByTitleLike(keyWord))
//						Actor a = new ActorServiceLocator().getActor();
//						a.getAllMoviesByTitleLike(keyWord)
						for (MovieDTO m : getAllMoviesFromSoapWS(keyWord))
							foundMoviesMap.put(m.getId(), m);
						break;
					case "actor":
						System.out.println("Checked: " + c);
						for(ActorDTO a : ActorDAO.getAllByActorNameLke(keyWord))
							for(MovieDTO m : MovieDAO.getAllByActor(a))
								foundMoviesMap.put(m.getId(), m);
						break;
					case "genre":
						System.out.println("Checked: " + c);
//						for(GenreDTO g : GenreDAO.getAllByGenreNameLike(keyWord))
//							for(MovieDTO m : MovieDAO.getAllByGenre(g))
						for (MovieDTO m : getAllMoviesFromRESTWS(keyWord))
							foundMoviesMap.put(m.getId(), m);
						break;
					default:
						break;
				}
			}
		
		foundMoviesList = new ArrayList<>(foundMoviesMap.values());
		
		return null;
	}
	
	public List<MovieDTO> getAllMoviesFromSoapWS(String searchText) {
		List<MovieDTO> moviesList = new ArrayList<>();
		
		Actor a;
		
		try {
			a = new ActorServiceLocator().getActor();
			JSONArray jArray = new JSONArray(a.getAllMoviesByTitleLike(searchText));
			for (int i = 0; i < jArray.length(); i++){
				List<ActorDTO> actorsList = new ArrayList<>();
				List<GenreDTO> genresList = new ArrayList<>();
				JSONObject movieJson = jArray.getJSONObject(i);
				MovieDTO movieDTO = new MovieDTO();
				
				movieDTO.setId(movieJson.getInt("id"));
				movieDTO.setTitle(movieJson.getString("title"));
				String releaseDateStr = movieJson.getString("releaseDate");
				movieDTO.setReleaseDate((releaseDateStr.equals("null")) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateStr));
				movieDTO.setStoryline(movieJson.getString("storyLine"));
				movieDTO.setTrailerLocationType(movieJson.getInt("trailerLocationType"));
				movieDTO.setTrailerLocation(movieJson.getString("trailerLocation"));
				movieDTO.setRuntimeMinutes(movieJson.getInt("runtimeMinutes"));
				movieDTO.setRate(movieJson.getDouble("rate"));
				
				JSONArray actorsJsonArray = new JSONArray(movieJson.getString("actors"));
				for(int j = 0; j < actorsJsonArray.length(); j++) {
					JSONObject actorJson = actorsJsonArray.getJSONObject(j);
					ActorDTO actorDTO = new ActorDTO();
					actorDTO.setId(actorJson.getInt("id"));
					actorDTO.setName(actorJson.getString("name"));
					actorsList.add(actorDTO);
				}
				
				JSONArray genresJsonArray = new JSONArray(movieJson.getString("genres"));
				for(int k = 0; k < genresJsonArray.length(); k++) {
					JSONObject genreJson = genresJsonArray.getJSONObject(k);
					GenreDTO genreDTO = new GenreDTO();
					genreDTO.setId(genreJson.getInt("id"));
					genreDTO.setName(genreJson.getString("name"));
					genresList.add(genreDTO);
				}
				
				moviesList.add(movieDTO);
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return moviesList;
	}
	
	public List<MovieDTO> getAllMoviesFromRESTWS(String searchText) {
		List<MovieDTO> moviesList = new ArrayList<>();
		
		BufferedReader in = null;
		
		try {
			String urlString = "http://localhost:8080/GenreREST/movieByGenre/" + searchText; 
			in = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()));
			
			JSONArray jArray = new JSONArray(in.readLine());
			
			for (int i = 0; i < jArray.length(); i++){
				List<ActorDTO> actorsList = new ArrayList<>();
				List<GenreDTO> genresList = new ArrayList<>();
				JSONObject movieJson = jArray.getJSONObject(i);
				MovieDTO movieDTO = new MovieDTO();
				
				movieDTO.setId(movieJson.getInt("id"));
				movieDTO.setTitle(movieJson.getString("title"));
				String releaseDateStr = movieJson.getString("releaseDate");
				movieDTO.setReleaseDate((releaseDateStr.equals("null")) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateStr));
				movieDTO.setStoryline(movieJson.getString("storyLine"));
				movieDTO.setTrailerLocationType(movieJson.getInt("trailerLocationType"));
				movieDTO.setTrailerLocation(movieJson.getString("trailerLocation"));
				movieDTO.setRuntimeMinutes(movieJson.getInt("runtimeMinutes"));
				movieDTO.setRate(movieJson.getDouble("rate"));
				
				JSONArray actorsJsonArray = new JSONArray(movieJson.getString("actors"));
				for(int j = 0; j < actorsJsonArray.length(); j++) {
					JSONObject actorJson = actorsJsonArray.getJSONObject(j);
					ActorDTO actorDTO = new ActorDTO();
					actorDTO.setId(actorJson.getInt("id"));
					actorDTO.setName(actorJson.getString("name"));
					actorsList.add(actorDTO);
				}
				
				JSONArray genresJsonArray = new JSONArray(movieJson.getString("genres"));
				for(int k = 0; k < genresJsonArray.length(); k++) {
					JSONObject genreJson = genresJsonArray.getJSONObject(k);
					GenreDTO genreDTO = new GenreDTO();
					genreDTO.setId(genreJson.getInt("id"));
					genreDTO.setName(genreJson.getString("name"));
					genresList.add(genreDTO);
				}
				
				moviesList.add(movieDTO);
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return moviesList;
	}
	
	public String details(int userId, MovieDTO movie) {
		movie.setRate(UserHasMovieDAO.getRateSum(movie.getId()));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movie", movie);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("userId", userId);
		return "movie?faces-redirect=true";
	}
	
	
	public String addMovie(){
		try {
			
			//array of actors out of string from form
			String[] actorsToAddWhileAddingMovie = actorName.split(",");
			//clear actors string for the subsequent use
			actorName = "";
			
			//insert all actors who haven't existed in database through Soap ws
			//Soap
			Actor a = new ActorServiceLocator().getActor();
			for(String actor : actorsToAddWhileAddingMovie)
				a.insertActor(actor);
			
			//uplaod movie
			if(movieInsert.getTrailerLocationType() == 1)
				movieInsert.setTrailerLocation(uploadMovie());

			//add movie to database
			int movieId = MovieDAO.insert(movieInsert);
			if (movieId > 0){
				System.out.println("Successfully added movie " + movieInsert.getTitle());

				//add relations between movie and gneres
				//form creates array with first null element if "select" is chosen
				if(selectedGenres != null && selectedGenres[0] != null)
					for(Integer genreId : selectedGenres)
						MovieHasGenreDAO.insert(movieId, genreId);
				//clear genre property for the subsequent use
				selectedGenres = null;
				
				//add relations between movie and actors
				Map<String, ActorDTO> mapOfAllActors = ActorDAO.getAllActorsNameMap();
				for(String actor : actorsToAddWhileAddingMovie){
					MovieHasActorDAO.insert(movieId, mapOfAllActors.get(actor).getId());
				}
			} else
				System.out.println("Not added movie " + movieInsert.getTitle());

			//clear movie property for the subsequent use
			movieInsert = new MovieDTO();

			return null;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			movieInsert = new MovieDTO();
			return null;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			movieInsert = new MovieDTO();
			return null;
		}
		
	}
	
	/*
	 * Returns string path of the uploaded movie.
	 */
	public String uploadMovie() {
		try(InputStream in = moviePart.getInputStream()) {
			String dirPath = prop.getProperty("upload.trailer.location");
			File dir = new File(dirPath);
			if(dir.exists()) {
				String fileName = JSFUtil.getFilename(moviePart);
				System.out.println("naziv trailera: |" + fileName + "|");
				if(fileName.endsWith(".mp4") || fileName.endsWith(".MP4")) {
					String filePath = dirPath + File.separator + fileName;
					File f = new File(filePath);
					if (!f.exists()) {
						Files.copy(in, new File(filePath).toPath());
						System.out.println("Uploaded file: " + filePath);
						
						return fileName;
					} else {
						System.out.println("Upload file \"" + fileName + "\" already exists");
						return fileName;
					}
					
				} else {
					System.out.println("Wrong upload file format!");
					return null;
				}
	
			} else {
				System.out.println("Directory \"" + dirPath + "\" for upload does not exist");
				return null;
			}
		} catch (IOException e) {
//			System.out.println("null pointeereeeeeer exceptionngggggggggggggggggggggggggggggg");
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Something with moviePart: ");
			System.out.println(moviePart);
			return null;
		}
	
	}

    

    public String delete() {
//		System.out.println("delete");

    	String retVal = "guest?faces-redirect=true";
    	if(MovieDAO.delete(movieSelected) > 0) {
    		retVal = "guest?faces-redirect=true";
    	} else 
    		retVal = null;

    	return retVal;
    }
    
    public String edit() {
//		System.out.println("edit " + movieSelected.getTitle());
		
		editable = true;
    	
		setActorsString(getActorsString());

    	
    	try {
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movieEdit", (MovieDTO) movieSelected.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movie", movieSelected);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("editable", editable);
    	
    	return "movie?faces-redirect=true";
    	//return null;
    }
    
    public String save() {
//		System.out.println("save");
		
		
		
		//set editable to false to disable editable fields on view
    	editable = false;
    	
    	//delete selected actors from movie (its relations)
    	for(String a : actorsToDeleteOnSaveList) {
    		MovieHasActorDAO.delete(movieEdit.getId(), a);
    	}
    	
    	//array of actors out of string from form
    	String[] actorsToAddList = null;
    	if(actorName.length() > 0)
    		actorsToAddList = actorName.split(",");
		//clear actors string for the subsequent use
		actorName = "";
    	
		//insert all actors who haven't existed in database through Soap ws
		//Soap
		Actor a;
		try {
			a = new ActorServiceLocator().getActor();
			if(actorsToAddList != null)
				for(String actor : actorsToAddList)
					a.insertActor(actor);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//add relations between movie and actors
		Map<String, ActorDTO> mapOfAllActors = ActorDAO.getAllActorsNameMap();
		if(actorsToAddList != null)
			for(String actor : actorsToAddList){
				MovieHasActorDAO.insert(movieEdit.getId(), mapOfAllActors.get(actor).getId());
				if(!movieEdit.getActors().contains(actor))
					movieEdit.getActors().add(actor);
			}
		

		MovieHasGenreDAO.deleteByMovieId(movieEdit.getId());
		for(Integer genreId : selectedGenres)
			MovieHasGenreDAO.insert(movieEdit.getId(), genreId);
		movieEdit.setGenres(MovieHasGenreDAO.getGenresByMovieId(movieEdit.getId()));

		//upload movie
		if(moviePart != null && movieEdit.getTrailerLocationType() == 1){
			movieEdit.setTrailerLocation(uploadMovie());
		}

		//update movie i table movies
		MovieDAO.update(movieEdit);
				
    	//#{&#160;} za space
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movie", movieEdit);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movieEdit", new MovieDTO());
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("editable", editable);
//    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("multiPart", moviePart);

    	
    	actorsToDeleteOnSaveList = new ArrayList<>();
    	selectedGenres = null;
    	
    	return "movie?faces-redirect=true";

    	//return null;
    }
    
    public String cancel() {
//		System.out.println("cancel");

    	editable = false;
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movie", movieSelected);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movieEdit", new MovieDTO());
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().put("editable", editable);

    	actorsToDeleteOnSaveList = new ArrayList<>();
    	selectedGenres = null;
    	
    	return "movie?faces-redirect=true";
    }
    
    public String deleteActor(String actor) {
    	movieEdit.getActors().remove(actor);
    	actorsToDeleteOnSaveList.add(actor);

    	return null;
    }
    
    public String cancleAddMovie() {
//    	actorName = "";
//    	selectedGenres = null;
//    	movieInsert = new MovieDTO();

    	return "guest?faces-redirect=true";
    }
    
    public void onRate(RateEvent rateEvent) {
    	Integer rate = Integer.valueOf((String)rateEvent.getRating());
//    	System.out.println("rate: " + rate);
    	UserHasMovieDTO userHasMovieDTO = UserHasMovieDAO.getById(userId, movieSelected.getId());
    	if(userHasMovieDTO == null){
    		userHasMovieDTO = new UserHasMovieDTO();
    		userHasMovieDTO.setUserId(userId);
    		userHasMovieDTO.setMovieId(movieSelected.getId());
    		userHasMovieDTO.setRate(rate);
    		userHasMovieDTO.setFavorite(null);
    		UserHasMovieDAO.insert(userHasMovieDTO);
//    		System.out.println("onRate insert");
    	} else{
    		userHasMovieDTO.setRate(rate);
    		UserHasMovieDAO.updateRate(userHasMovieDTO);
//    		System.out.println("onRate update");

    	}
    	userHasMovie = userHasMovieDTO;
    	//userHasMovie = UserHasMovieDAO.getById(userId, movieSelected.getId());
    	
//    	System.out.println("onRate nakon get userHasMovie.rate: " + userHasMovieDTO.getRate());
    }
    
    public void onFavorite(RateEvent rateEvent) {
    	Integer favorite = Integer.valueOf((String) rateEvent.getRating());
    	UserHasMovieDTO userHasMovieDTO = UserHasMovieDAO.getById(userId, movieSelected.getId());
    	if(userHasMovieDTO == null) {
    		userHasMovieDTO = new UserHasMovieDTO();
    		userHasMovieDTO.setUserId(userId);
    		userHasMovieDTO.setMovieId(movieSelected.getId());
    		userHasMovieDTO.setRate(null);
    		userHasMovieDTO.setFavorite(favorite);
    		UserHasMovieDAO.insert(userHasMovieDTO);
    	} else {
			userHasMovieDTO.setFavorite(favorite);
			UserHasMovieDAO.updateFavorite(userHasMovieDTO);
		}
    	userHasMovie = userHasMovieDTO;
    }
    
    public String addComment() {
    	if(comment != null && !comment.equals("")){
    		UserHasMovieCommentDAO.insert(userId, movieSelected.getId(), comment);
        	commentsList = UserHasMovieCommentDAO.getByMovieId(movieSelected.getId());
        	comment = null;
    	}

    	return null;
    }
    
    public String deleteComment(CommentDTO c) {
    	UserHasMovieCommentDAO.delete(c.getId());
    	commentsList = UserHasMovieCommentDAO.getByMovieId(movieSelected.getId());
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

//	public String getGenresStringFromInput() {
//		return genresStringFromInput;
//	}
//
//	public void setGenresStringFromInput(String genersStringFromInput) {
//		this.genresStringFromInput = genersStringFromInput;
//	}

//	public String getGenresStringToShowOnForm() {
//		List<GenreDTO> genreDTOList = GenreDAO.getAll();
//		List<String> stringList = new ArrayList<>();
//		for(GenreDTO g : genreDTOList)
//			stringList.add(g.getName());
//		JSONArray jArr = new JSONArray(stringList);
//		genresStringToShowOnForm = jArr.toString();
//		return genresStringToShowOnForm;
//	}

//	public void setGenresStringToShowOnForm(String genersStringToShowOnForm) {
//		this.genresStringToShowOnForm = genersStringToShowOnForm;
//	}

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

	public Part getMoviePart() {
		return moviePart;
	}

	public void setMoviePart(Part moviePart) {
		this.moviePart = moviePart;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public MovieDTO getMovieEdit() {
		return movieEdit;
	}

	public void setMovieEdit(MovieDTO movieEdit) {
		this.movieEdit = movieEdit;
	}

	public List<GenreDTO> getGenresAllList() {
		return genresAllList;
	}

	public void setGenresAllList(List<GenreDTO> genresAllList) {
		this.genresAllList = genresAllList;
	}

	public Double getMovieRate() {
		return movieRate;
	}

	public void setMovieRate(Double movieRate) {
		this.movieRate = movieRate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public UserHasMovieDTO getUserHasMovie() {
		return userHasMovie;
	}

	public void setUserHasMovie(UserHasMovieDTO userHasMovie) {
		this.userHasMovie = userHasMovie;
	}

	public List<CommentDTO> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<CommentDTO> commentsList) {
		this.commentsList = commentsList;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String[] getSearchCriteriaCheckBoxes() {
		return searchCriteriaCheckBoxes;
	}

	public void setSearchCriteriaCheckBoxes(String[] searchCriteriaCheckBoxes) {
		this.searchCriteriaCheckBoxes = searchCriteriaCheckBoxes;
	}

	public Map<String, String> getSearchCriteriaPredefinedValues() {
		return searchCriteriaPredefinedValues;
	}

	public void setSearchCriteriaPredefinedValues(Map<String, String> searchCriteriaPredefinedValues) {
		this.searchCriteriaPredefinedValues = searchCriteriaPredefinedValues;
	}



	
	
	
	
}
