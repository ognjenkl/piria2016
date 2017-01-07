package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieHasGenreDAO {

	private static final String SQL_INSERT = "INSERT INTO movies_has_genres (movies_id, genres_id) VALUES (?, ?);";
	private static final String SQL_GET_ALL_GENRES_BY_MOVIE_ID = "SELECT g.name FROM movies_has_genres mg JOIN genres g ON g.id = mg.genres_id WHERE mg.movies_id = ?;";

	public static int insert(int movieId, int genreId){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT);
			ppst.setInt(1, movieId);
			ppst.setInt(2, genreId);
			
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
	
	
	public static List<String> getGenresByMovieId(int movieId){
		List<String> genresList = new ArrayList<>();
		
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_GENRES_BY_MOVIE_ID);
			ppst.setInt(1, movieId);
			resultSet = ppst.executeQuery();
			
			while(resultSet.next()){
				genresList.add(resultSet.getString(1));
			}
			
			ppst.close();

			if(genresList.size() > 0)
				return genresList;
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

