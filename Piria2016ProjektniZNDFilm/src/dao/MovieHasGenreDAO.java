package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.GenreDTO;

public class MovieHasGenreDAO {

	private static final String SQL_INSERT = "INSERT INTO movies_has_genres (movies_id, genres_id) VALUES (?, ?);";
	private static final String SQL_GET_ALL_GENRES_BY_MOVIE_ID = "SELECT g.id, g.name FROM movies_has_genres mg JOIN genres g ON g.id = mg.genres_id WHERE mg.movies_id = ?;";
	private static final String SQL_DELETE = "DELETE mhg FROM movies_has_genres mhg JOIN genres g ON g.id = mhg.genres_id WHERE movies_id=? and g.name=?;";
	private static final String SQL_BY_MOVIE_ID = "SELECT * FROM movies_has_genres WHERE movies_id=?;";
	private static final String SQL_DELETE_BY_MOVIE_ID = "DELETE FROM movies_has_genres WHERE movies_id=?;";
	
	
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
	
	
	public static List<GenreDTO> getGenresByMovieId(int movieId){
		List<GenreDTO> genresList = new ArrayList<>();
		
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_GENRES_BY_MOVIE_ID);
			ppst.setInt(1, movieId);
			resultSet = ppst.executeQuery();
			
			while(resultSet.next()){
				GenreDTO genre = new GenreDTO();
				genre.setId(resultSet.getInt(1));
				genre.setName(resultSet.getString(2));
				genresList.add(genre);
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
	
	public static Integer[] getGenreIdListByMovieId(int movieId){
		List<Integer> genreIdsList = new ArrayList<>();
		
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_BY_MOVIE_ID);
			ppst.setInt(1, movieId);
			resultSet = ppst.executeQuery();
			
			while(resultSet.next()){
				genreIdsList.add(resultSet.getInt(2));
			}
			
			ppst.close();

			if(genreIdsList.size() > 0)
				return genreIdsList.toArray(new Integer[genreIdsList.size()]);
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
	
	
	public static int delete(int movieId, String genre){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_DELETE);
			ppst.setInt(1, movieId);
			ppst.setString(2, genre);
			
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
	
	public static int deleteByMovieId(int movieId){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_DELETE_BY_MOVIE_ID);
			ppst.setInt(1, movieId);
			
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
	
	
	

}

