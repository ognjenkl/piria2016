package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import dto.ActorDTO;
import dto.MovieDTO;

public class MovieHasActorDAO {

	private static final String SQL_INSERT = "INSERT INTO movies_has_actors (movies_id, actors_id) VALUES (?, ?);";
	private static final String SQL_GET_ALL_ACTORS_BY_MOVIE_ID = "SELECT a.name FROM movies_has_actors ma JOIN actors a ON a.id = ma.actors_id WHERE ma.movies_id = ?;";

	
	public static int insert(int movieId, int actorId){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT);
			ppst.setInt(1, movieId);
			ppst.setInt(2, actorId);
			
			int rowCount = ppst.executeUpdate();
			if (rowCount > 0)
				retVal = rowCount;
			
			
			ppst.close();
			return retVal;
		} catch (Exception e){
			//TODO log
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	public static List<String> getActorsByMovieId(int movieId){
		List<String> actorsList = new ArrayList<>();
		
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_ACTORS_BY_MOVIE_ID);
			ppst.setInt(1, movieId);
			resultSet = ppst.executeQuery();
			
			while(resultSet.next()){
				actorsList.add(resultSet.getString(1));
			}
			
			ppst.close();

			if(actorsList.size() > 0)
				return actorsList;
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
	
	

}
