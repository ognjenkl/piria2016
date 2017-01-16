package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.MovieDTO;

public class FavoriteMovieListHasMovieDAO {

	private static final String SQL_GET_ALL_BY_FAVORITE_MOIVE_LIST_ID = "";
	
	
	

	public static List<MovieDTO> getAllByFavoriteMovieListId(Integer favoriteMovieListId) {
		List<MovieDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_BY_FAVORITE_MOIVE_LIST_ID);
			ppst.setInt(1, favoriteMovieListId);
		
	
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				MovieDTO movie = MovieDAO.getById(resultSet.getInt("movies_id"));
				retVal.add(movie);
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
