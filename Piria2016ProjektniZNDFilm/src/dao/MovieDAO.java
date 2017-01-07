package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import com.mysql.jdbc.Statement;

import dto.ActorDTO;
import dto.MovieDTO;

/**
 * @author ognjen
 *
 */
public class MovieDAO {

	static List<MovieDTO> list = null;
	
	private static final String SQL_INSERT = "INSERT INTO movies (title, release_date, storyline, trailer_location_type, trailer_location, runtime_minutes) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SQL_GET_BY_TITLE = "SELECT * FROM movies WHERE active = 1 AND title LIKE ?;";
	private static final String SQL_DELETE = "UPDATE movies SET active=0 WHERE id=?";
	private static final String SQL_UPDATE = "UPDATE movies SET title=?, release_date=? , storyline=?, trailer_location_type=?, trailer_location=?, runtime_minutes=? WHERE id=?";
	
	//actors, genres

	
	public static List<MovieDTO> getByTitle(String title){
		List<MovieDTO> movieList = new ArrayList<>();
		
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_BY_TITLE);
			ppst.setString(1, "%" + title + "%");
			resultSet = ppst.executeQuery();
			
			while(resultSet.next()){
				MovieDTO movie = new MovieDTO();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setStoryline(resultSet.getString(4));
				movie.setTrailerLocationType(resultSet.getInt(5));
				movie.setTrailerLocation(resultSet.getString(6));
				movie.setRuntimeMinutes(resultSet.getInt(7));
				movie.setRate(resultSet.getInt(8));
				
				movie.setActors(MovieHasActorDAO.getActorsByMovieId(movie.getId()));
				movie.setGenres(MovieHasGenreDAO.getGenresByMovieId(movie.getId()));
				
				movieList.add(movie);
			}
			
			ppst.close();

			if(movieList.size() > 0)
				return movieList;
			else 
				return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}


	public static int insert(MovieDTO movie){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		ResultSet resultSet = null;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ppst.setString(1, movie.getTitle());
			if (movie.getReleaseDate() != null)
				ppst.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
			else
				ppst.setNull(2, Types.NULL);
			if (movie.getStoryline() != null)
				ppst.setString(3, movie.getStoryline());
			else
				ppst.setNull(3, Types.NULL);
			if (movie.getTrailerLocationType() != null)
				ppst.setInt(4, movie.getTrailerLocationType());
			else
				ppst.setNull(4, Types.NULL);
			if (movie.getTrailerLocation() != null)
				ppst.setString(5, movie.getTrailerLocation());
			else
				ppst.setNull(5, Types.NULL);
			if (movie.getRuntimeMinutes() != null)
				ppst.setInt(6, movie.getRuntimeMinutes());
			else
				ppst.setNull(6, Types.NULL);
			
			
			int rowCount = ppst.executeUpdate();
			resultSet = ppst.getGeneratedKeys();
			
			if (rowCount > 0 && resultSet.next())
				retVal = resultSet.getInt(1);
			
			ppst.close();
			return retVal;
		} catch (Exception e){
			//TODO log
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	

	public static int delete(MovieDTO movie){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		ResultSet resultSet = null;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_DELETE);
			ppst.setInt(1, movie.getId());
			
			retVal = ppst.executeUpdate();
			
			
			ppst.close();
			return retVal;
		} catch (Exception e){
			//TODO log
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	

}
