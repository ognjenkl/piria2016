package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.FavoriteListDTO;

public class FavoriteMovieListDAO {

	private static final String SQL_GET_ALL_BY_USER_ID = "";
	private static final String SQL_INSERT = "INSERT INTO favorite_movies_lists (name, users_id) VALUES (?,?)";
	
	
	public static List<FavoriteListDTO> getAllByUserId(Integer userId) {
		List<FavoriteListDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_BY_USER_ID);
			ppst.setInt(1, userId);
	
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()) {
				FavoriteListDTO f = new FavoriteListDTO();
				f.setId(resultSet.getInt(1));
				f.setName(resultSet.getString(2));
				f.setCreateDate(resultSet.getTimestamp(3));
				f.setUsersId(userId);

				f.setMovies(FavoriteMovieListHasMovieDAO.getAllByFavoriteMovieListId(f.getId()));

				retVal.add(f);
			}
			
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
	
	
	
	public static Integer insert(String name, Integer userId) {
		Integer retVal = null;
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ppst.setString(1, name);
			ppst.setInt(2, userId);
	
			int rowCount = ppst.executeUpdate();
			resultSet = ppst.getGeneratedKeys();
			
			if (rowCount > 0 && resultSet.next()){
				retVal = resultSet.getInt(1);
			}
			
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
	
	
	

//	public static Integer insert(Integer userId, Integer movieId, String comment) {
//	Integer retVal = null;
//	Connection conn = null;
//	PreparedStatement ppst = null;
//	ResultSet resultSet = null;
//	
//	try {
//		conn = ConnectionPool.getConnectionPool().checkOut();
//		ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
//		ppst.setInt(1, userId);
//		ppst.setInt(2, movieId);
//		ppst.setString(3, comment);
//
//		int rowCount = ppst.executeUpdate();
//		resultSet = ppst.getGeneratedKeys();
//		
//		if (rowCount > 0 && resultSet.next()){
//			retVal = resultSet.getInt(1);
//		}
//		
//		ppst.close();
//		return retVal;
//		
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		return null;
//	} finally {
//		ConnectionPool.getConnectionPool().checkIn(conn);
//	}
//	
//}
	
	
	
	
}
