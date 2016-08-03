package dto;


import java.util.Date;
import java.util.List;

/**
 * @author ognjen
 *
 */
public class MovieDTO {

	String title;
	Date releaseDate;
	List<String> actors;
	String storyline;
	List<String> genres;
	String trailerLocation;
	int runtimeMinutes;
	int rate;
	String movieLocation;
	
	
	
	public MovieDTO(String title, Date releaseDate, List<String> actors, String storyline,
			List<String> genres, String trailerLocation, int runtimeMinutes, int rate, String movieLocation){
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

	
	public MovieDTO(){
		
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


	public int getRuntimeMinutes() {
		return runtimeMinutes;
	}



	public void setRuntimeMinutes(int runtimeMinutes) {
		this.runtimeMinutes = runtimeMinutes;
	}



	public int getRate() {
		return rate;
	}



	public void setRate(int rate) {
		this.rate = rate;
	}



	public String getMovieLocation() {
		return movieLocation;
	}



	public void setMovieLocation(String movieLocation) {
		this.movieLocation = movieLocation;
	}



	public String getTrailerLocation() {
		return trailerLocation;
	}



	public void setTrailerLocation(String trailerLocation) {
		this.trailerLocation = trailerLocation;
	}
	
	

}
