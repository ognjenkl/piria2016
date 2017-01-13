package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import dto.CommentDTO;

public class UserHasMovieCommentDAO {

	private static final String SQL_GET_BY_ID = "SELECT * FROM users_has_movies_comments WHERE active=1 AND comment_id=?;";
	private static final String SQL_GET_BY_MOVIE_ID = "SELECT * FROM users_has_movies_comments WHERE active=1 AND movies_id=?;";
	private static final String SQL_GET_BY_USER_AND_MOVIE_ID = "SELECT * FROM users_has_movies_comments WHERE active=1 AND users_id=? AND movies_id=?;";
	private static final String SQL_INSERT = "INSERT INTO users_has_movies_comments (users_id, movies_id, comment) VALUES (?,?,?)";
	private static final String SQL_DELETE = "UPDATE users_has_movies_comments SET active=0 WHERE comment_id=?;";


	public static CommentDTO getById(Integer commentId) {
		CommentDTO retVal = null;
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_BY_ID);
			ppst.setInt(1, commentId);
			resultSet = ppst.executeQuery();
			
			if (resultSet.next()){
				retVal = new CommentDTO();
				retVal.setId(resultSet.getInt(1));
				retVal.setUserId(resultSet.getInt(2));
				retVal.setMovieId(resultSet.getInt(3));
				retVal.setComment(resultSet.getString(4));
				retVal.setComment_date(resultSet.getTimestamp(5));
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

	public static List<CommentDTO> getByMovieId(Integer movieId) {
		List<CommentDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_BY_MOVIE_ID);
			ppst.setInt(1, movieId);
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setId(resultSet.getInt(1));
				commentDTO.setUserId(resultSet.getInt(2));
				commentDTO.setMovieId(resultSet.getInt(3));
				commentDTO.setComment(resultSet.getString(4));
				commentDTO.setComment_date(resultSet.getTimestamp(5));
				commentDTO.setUser(UserDAO.getById(commentDTO.getUserId()));
				retVal.add(commentDTO);
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
	
	public static List<CommentDTO> getByUserAndMovieId(Integer userId, Integer movieId) {
		List<CommentDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_BY_USER_AND_MOVIE_ID);
			ppst.setInt(1, userId);
			ppst.setInt(2, movieId);
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setId(resultSet.getInt(1));
				commentDTO.setUserId(resultSet.getInt(2));
				commentDTO.setMovieId(resultSet.getInt(3));
				commentDTO.setComment(resultSet.getString(4));
				commentDTO.setComment_date(resultSet.getTimestamp(5));
				commentDTO.setUser(UserDAO.getById(userId));
				retVal.add(commentDTO);
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
	
	public static Integer insert (Integer userId, Integer movieId, String comment) {
		Integer retVal = null;
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ppst.setInt(1, userId);
			ppst.setInt(2, movieId);
			ppst.setString(3, comment);

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
	
	public static Integer delete(Integer commentId) {
		Integer retVal = null;
		Connection conn = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_DELETE);
			ppst.setInt(1, commentId);

			int rowCount = ppst.executeUpdate();
			
			if (rowCount > 0){
				retVal = rowCount;
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
	
	
	
	
//	public static Integer insert (Integer userId, Integer movieId, String comment) {
//		Integer retVal = null;
//		Connection conn = null;
//		PreparedStatement ppst = null;
//		ResultSet resultSet = null;
//		
//		try {
//			conn = ConnectionPool.getConnectionPool().checkOut();
//			ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
//			ppst.setInt(1, userId);
//			ppst.setInt(2, movieId);
//			ppst.setString(3, comment);
//
//			int rowCount = ppst.executeUpdate();
//			resultSet = ppst.getGeneratedKeys();
//			
//			if (rowCount > 0 && resultSet.next()){
//				retVal = resultSet.getInt(1);
//			}
//			
//			ppst.close();
//			return retVal;
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		} finally {
//			ConnectionPool.getConnectionPool().checkIn(conn);
//		}
//		
//	}
	
}
