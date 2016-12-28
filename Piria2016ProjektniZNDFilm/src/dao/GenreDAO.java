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

	static final String SQL_ALL = "SELECT * FROM genres;";
	static final String SQL_INSERT = "INSERT INTO genres (name) VALUES (?);";

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
	

}
