package dto;


import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author ognjen
 *
 */
public class MovieDTO implements Serializable {

	private static final long serialVersionUID = 2153049650204486652L;
	private int id;
	private String title;
	private Date releaseDate;
	private List<String> actors;
	private String storyline;
	private List<String> genres;
	private String trailerLocation;
	private Integer runtimeMinutes;
	private Integer rate;
	private Integer movieLocationType;
	private String movieLocation;
	private Date addedDate;
	private boolean active;
	
	
	public MovieDTO() {
		
	}
	
	public MovieDTO(String title, Date releaseDate, List<String> actors, String storyline,
			List<String> genres, String trailerLocation, Integer runtimeMinutes, Integer rate, String movieLocation){
		this.title = title;
		this.releaseDate = releaseDate;
		this.actors = actors;
		this.storyline = storyline;
		this.genres = genres;
		this.trailerLocation = trailerLocation;
		this.runtimeMinutes = runtimeMinutes;
		this.rate = rate;
		this.movieLocation = movieLocation;
			
	}

	



	
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Date getReleaseDate() {
		return releaseDate;
	}


	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}


	public List<String> getActors() {
		return actors;
	}


	public void setActors(List<String> actors) {
		this.actors = actors;
	}


	public String getStoryline() {
		return storyline;
	}


	public void setStoryline(String storyline) {
		this.storyline = storyline;
	}


	public List<String> getGenres() {
		return genres;
	}


	public void setGenres(List<String> genres) {
		this.genres = genres;
	}


	public String getTrailerLocation() {
		return trailerLocation;
	}


	public void setTrailerLocation(String trailerLocation) {
		this.trailerLocation = trailerLocation;
	}


	public Integer getRuntimeMinutes() {
		return runtimeMinutes;
	}


	public void setRuntimeMinutes(Integer runtimeMinutes) {
		this.runtimeMinutes = runtimeMinutes;
	}


	public Integer getRate() {
		return rate;
	}


	public void setRate(Integer rate) {
		this.rate = rate;
	}


	public String getMovieLocation() {
		return movieLocation;
	}


	public void setMovieLocation(String movieLocation) {
		this.movieLocation = movieLocation;
	}


	public Date getAddedDate() {
		return addedDate;
	}


	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getMovieLocationType() {
		return movieLocationType;
	}

	public void setMovieLocationType(Integer movieLocationType) {
		this.movieLocationType = movieLocationType;
	}
	
	
	
	

}
