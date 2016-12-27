package dto;


import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author ognjen
 *
 */
public class MovieDTO {

	int id;
	String title;
	Date releaseDate;
	List<String> actors;
	String storyline;
	List<String> genres;
	String trailerLocation;
	Integer runtimeMinutes;
	Integer rate;
	String movieLocation;
	Date addedDate;
	boolean active;
	
	
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
	
	
	
	

}
