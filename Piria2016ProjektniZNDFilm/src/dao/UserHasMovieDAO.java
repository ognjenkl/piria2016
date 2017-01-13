package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import dto.UserHasMovieDTO;

public class UserHasMovieDAO {

	private static final String SQL_GET_BY_ID = "SELECT * FROM users_has_movies WHERE users_id=? AND movies_id=?;";
	private static final String SQL_GET_RATE_SUM_BY_MOVIE_ID = "SELECT sum(rate)/count(*) FROM users_has_movies WHERE movies_id=?;";
	private static final String SQL_GET_FAVORITE = "SELECT favorite FROM users_has_movies WHERE users_id=? AND movies_id=?;";
	private static final String SQL_GET_RATE = "SELECT rate FROM users_has_movies WHERE users_id=? AND movies_id=?;";
	private static final String SQL_INSERT = "INSERT INTO users_has_movies (users_id, movies_id, favorite, rate) VALUES (?, ?, ?, ?);";
	private static final String SQL_UPDATE_RATE = "UPDATE users_has_movies SET rate=? WHERE users_id=? AND movies_id=?;";
	private static final String SQL_UPDATE_FAVORITE = "UPDATE users_has_movies SET favorite=? WHERE users_id=? AND movies_id=?;";
	
	
	public static UserHasMovieDTO getById(int userId, int movieId) {
		UserHasMovieDTO uhm = null;
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_BY_ID);
			ppst.setInt(1, userId);
			ppst.setInt(2, movieId);
			resultSet = ppst.executeQuery();
			
			if(resultSet.next()){
				uhm = new UserHasMovieDTO();
				uhm.setUserId(resultSet.getInt(1));
				uhm.setMovieId(resultSet.getInt(2));
				uhm.setFavorite(resultSet.getInt(3));
				uhm.setRate(resultSet.getInt(4));
				
			}
		
			ppst.close();
			
			return uhm;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	public static Double getRateSum(int movieId) {
		Double retVal = null;
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_RATE_SUM_BY_MOVIE_ID);
			ppst.setInt(1, movieId);
			resultSet = ppst.executeQuery();
			
			

			if(resultSet.next())
				retVal = resultSet.getDouble(1);
			
			ppst.close();
			return retVal;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	public static Double getRate(int userId, int movieId) {
		Double retVal = null;
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_RATE);
			ppst.setInt(1, userId);
			ppst.setInt(2, movieId);
			resultSet = ppst.executeQuery();
			
			if(resultSet.next())
				retVal = resultSet.getDouble(1);
			
			ppst.close();
			return retVal;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	public static Integer getFavorite(int userId, int movieId) {
		Integer retVal = null;
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_FAVORITE);
			ppst.setInt(1, userId);
			ppst.setInt(2, movieId);
			resultSet = ppst.executeQuery();
			
			if(resultSet.next())
				retVal = resultSet.getInt(1);

			ppst.close();
			
			return retVal;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	public static int insert(UserHasMovieDTO uhm){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT);
			ppst.setInt(1, uhm.getUserId());
			ppst.setInt(2, uhm.getMovieId());
			if (uhm.getFavorite() != null)
				ppst.setInt(3, uhm.getFavorite());
			else
				ppst.setNull(3, Types.NULL);
			if (uhm.getRate() != null)
				ppst.setInt(4, uhm.getRate());
			else
				ppst.setNull(4, Types.NULL);
			
			
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

	
	public static int updateRate(UserHasMovieDTO uhm){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_UPDATE_RATE);
			ppst.setInt(1, uhm.getRate());
			ppst.setInt(2, uhm.getUserId());
			ppst.setInt(3, uhm.getMovieId());
			
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
	
	
	public static int updateFavorite(UserHasMovieDTO uhm){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_UPDATE_FAVORITE);
			ppst.setInt(1, uhm.getFavorite());
			ppst.setInt(2, uhm.getUserId());
			ppst.setInt(3, uhm.getMovieId());
			
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
