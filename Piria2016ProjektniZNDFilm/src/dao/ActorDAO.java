package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dto.ActorDTO;

public class ActorDAO {

	
	private static final String SQL_ALL = "SELECT * FROM zndfilm.actors;";
//	private static final String SQL_INSERT = "INSERT INTO actors (name) VALUES (?);";
	private static final String SQL_GET_ALL_BY_ACTOR_NAME_LIKE = "SELECT * FROM actors WHERE name LIKE ?;";
	
	
	public static Map<Integer, ActorDTO> getAllActorsMap(){
		Connection conn = null;
		ResultSet rs = null;
		Map<Integer, ActorDTO> actors = new TreeMap<>();
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_ALL);
			rs = ppst.executeQuery();

			while(rs.next())
				actors.put(rs.getInt("id"), new ActorDTO(rs.getInt("id"), rs.getString("name")));

			ppst.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return actors;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	
		return actors;
	}
	
	
	public static Map<String, ActorDTO> getAllActorsNameMap(){
		Connection conn = null;
		ResultSet rs = null;
		Map<String, ActorDTO> actors = new TreeMap<>();
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_ALL);
			rs = ppst.executeQuery();

			while(rs.next())
				actors.put(rs.getString("name"), new ActorDTO(rs.getInt("id"), rs.getString("name")));

			ppst.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return actors;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	
		return actors;
	}
	
	
	public static List<ActorDTO> getAllByActorNameLke(String actorName) {
		List<ActorDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_BY_ACTOR_NAME_LIKE);
			ppst.setString(1, "%" + actorName + "%");

			resultSet = ppst.executeQuery();
			
			if (resultSet.next()){
				ActorDTO actorDTO = new ActorDTO();
				actorDTO.setId(resultSet.getInt(1));
				actorDTO.setName(resultSet.getString(2));
				retVal.add(actorDTO);
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
