package bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;
import javax.xml.rpc.ServiceException;

import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.InstanceOfExpression;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actor.Actor;
import actor.ActorServiceLocator;
import dao.ActorDAO;
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
//@SessionScoped //zbog login bean-a, ne moze biti manji skope nego u login
@ViewScoped
//@RequestScoped
public class MovieBean implements Serializable{
	
	private static final long serialVersionUID = -6851375545924053833L;
	String keyWord;
	//lista filmova trazenih u pretrazi
	//koristi se       	<Context docBase="G:\filmUpload" path="/movies/" /> na Tomcat serveru u server.xml
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
	
	Part moviePart;
	
	Properties prop;
	
	//for movie edit
	boolean editable;
	
	MovieDTO movieEdit;
	
	List<String> actorsToDeleteOnSaveList;
	
	List<GenreDTO> genresAllList;
	
	public MovieBean() {
		keyWord = null;
		foundMoviesList = null;
		movieSelected = null;
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
		
	}

	@PostConstruct
	public void init(){
		genreValues = new LinkedHashMap<>();
		List<GenreDTO> gens = GenreDAO.getAll();
		for (GenreDTO g : gens) 
			genreValues.put(g.getId(), g.getName().toString());
		
		movieSelected = (MovieDTO) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("movie");
		if(movieSelected != null) {
			System.out.println("init selected, movieSelected.title: " + movieSelected.getTitle());
			selectedGenres = MovieHasGenreDAO.getGenreIdListByMovieId(movieSelected.getId());
		}
		movieEdit = (MovieDTO) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("movieEdit");
		if (movieEdit != null)
			System.out.println("init edit, movieEdit.title:  " + movieEdit.getTitle());
		
		Object e = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("editable");
		if( e != null)
			editable = (boolean) e;
		
		setActorsString(getActorsString());
//		System.out.println("actors init: " + getActorsString());
		
		genresAllList = GenreDAO.getAll();
		
//		moviePart = (Part) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("multiPart");

//		System.out.println("geners and all list");
//		if (selectedGenres != null && selectedGenres[0] != null)
//			System.out.println("init selected genres: " + selectedGenres[0]);
//    	if (genresAllList != null && genresAllList.get(0) != null)
//    		System.out.println("init genres all list: " + genresAllList.get(0).getId());

	}
	
	
	public String search(){
		foundMoviesList = MovieDAO.getByTitle(keyWord);
		return null;
	}
	
	
	public String details(MovieDTO movie) {
		System.out.println("details ");
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movie", movie);
		return "movie?faces-redirect=true";
	}
	
	public String details2(MovieDTO movie){
		//movieSelected = movie;
		System.out.println("details 2");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("movie", movie);
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
		System.out.println("null pointeereeeeeer exceptionneeeee: " + moviePart);
		
		try(InputStream in = moviePart.getInputStream()) {
			System.out.println("null pointeereeeeeer exceptionnfffffffffffffffffffffffffff");
			String dirPath = prop.getProperty("upload.location");
			File dir = new File(dirPath);
			if(dir.exists()) {
				String fileName = getFilename(moviePart);
				System.out.println("naziv filma: " + fileName);
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
			System.out.println("null pointeereeeeeer exceptionngggggggggggggggggggggggggggggg");
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Something with moviePart: ");
			System.out.println(moviePart);
			return null;
		}
	
	}

    private static String getFilename(Part part) {
        // courtesy of BalusC : http://stackoverflow.com/a/2424824/281545
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1)
                        .substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public String delete() {
		System.out.println("delete");

    	String retVal = "guest?faces-redirect=true";
    	if(MovieDAO.delete(movieSelected) > 0) {
    		retVal = "guest?faces-redirect=true";
    	} else 
    		retVal = null;

    	return retVal;
    }
    
    public String edit() {
		System.out.println("edit " + movieSelected.getTitle());
		
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
		System.out.println("save");
		
		
		
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
		System.out.println("cancel");

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




	
	
	
	
}
