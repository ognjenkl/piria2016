package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import dto.GenreDTO;

public class GenreDAO {

	private static final String SQL_ALL = "SELECT * FROM genres WHERE active=1;";
	private static final String SQL_INSERT = "INSERT INTO genres (name) VALUES (?);";
	private static final String SQL_DELETE = "UPDATE genres SET	active=0 WHERE id=?";
	private static final String SQL_GET_ALL_BY_GENRE_NAME_LIKE = "SELECT * FROM genres WHERE active=1 AND name LIKE ?;";

	public GenreDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<GenreDTO> getAll() {
		Connection conn = null;
		ResultSet resultSet = null;
		List<GenreDTO> genreDTOs = new ArrayList<>();
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_ALL);
			resultSet = ppst.executeQuery();
			while(resultSet.next()){
				GenreDTO genreDTO = new GenreDTO();
				genreDTO.setId(resultSet.getInt(1));
				genreDTO.setName(resultSet.getString(2));
				genreDTOs.add(genreDTO);
			}
			
			ppst.close();

			return genreDTOs;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
		
	}
	
	
	public static List<GenreDTO> getAllByGenreNameLike(String genreName) {
		List<GenreDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_BY_GENRE_NAME_LIKE);
			ppst.setString(1, "%" + genreName + "%");

			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){	
				GenreDTO genreDTO = new GenreDTO();
				genreDTO.setId(resultSet.getInt(1));
				genreDTO.setName(resultSet.getString(2));
				retVal.add(genreDTO);
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
	
	public static int insert(GenreDTO genreDTO) {
		Connection conn = null;
		int retVal = -1;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ppst.setString(1, genreDTO.getName());

			int rowCount = ppst.executeUpdate();
			resultSet = ppst.getGeneratedKeys();
			
			if(rowCount > 0 && resultSet.next())
				retVal = resultSet.getInt(1);
			
			ppst.close();
			
			return retVal;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return retVal;
		}

	}
	
	/**
	 * Deletes a genre by id.
	 * 
	 * @param genreId id of genre to delete
	 * @return id of deleted genre
	 */
	public static int delete(int genreId) {
		Connection conn = null;
		int retVal = -1;
		PreparedStatement ppst = null;
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_DELETE);
			ppst.setInt(1, genreId);

			int rowCount = ppst.executeUpdate();
			
			if(rowCount > 0)
				retVal = genreId;
			
			ppst.close();
			
			return retVal;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return retVal;
		}
	}
	
	
	
//	public static Integer insert(Integer userId, Integer movieId, String comment) {
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
