package dto;

import java.util.Date;
import java.util.List;

public class FavoriteListDTO {

	private Integer id;
	private String name;
	private Date createDate;
	private Integer usersId;
	private List<MovieDTO> movies;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	public List<MovieDTO> getMovies() {
		return movies;
	}
	public void setMovies(List<MovieDTO> movies) {
		this.movies = movies;
	}
	
	
	
	
	

}
