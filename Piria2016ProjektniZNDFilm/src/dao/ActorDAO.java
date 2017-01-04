package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import dto.ActorDTO;

public class ActorDAO {

	
	private static final String SQL_ALL = "SELECT * FROM zndfilm.actors;";
	private static final String SQL_INSERT = "INSERT INTO actors (name) VALUES (?);";
	
	
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
	
	
}
