package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.EventDTO;

public class EventDAO {

	private static final String SQL_GET_ALL_INACTIVE = "SELECT * FROM events WHERE active=0;";
	private static final String SQL_INSERT = "INSERT INTO events (event_announcement, event_maintained, name, location) VALUES (?,?,?,?)";
	private static final String SQL_APPROVE = "UPDATE events SET active=1 WHERE id=?";

	public static List<EventDTO> getAllInactive() {
		List<EventDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_INACTIVE);
	
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				EventDTO eventDTO = new EventDTO();
				eventDTO.setId(resultSet.getInt(1));
				eventDTO.setEventAnnouncement(resultSet.getDate(2));
				eventDTO.setEventMaintained(resultSet.getDate(3));
				eventDTO.setName(resultSet.getString(4));
				eventDTO.setLocation(resultSet.getString(5));
				retVal.add(eventDTO);
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

	public static Integer insert(EventDTO eventDTO) {
		Integer retVal = null;
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ppst.setTimestamp(1, new java.sql.Timestamp(eventDTO.getEventAnnouncement().getTime()));
			ppst.setTimestamp(2, new java.sql.Timestamp(eventDTO.getEventMaintained().getTime()));
			ppst.setString(3, eventDTO.getName());
			ppst.setString(4, eventDTO.getLocation());
	
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
	
	public static Integer approve(Integer eventId) {
		Integer retVal = null;
		Connection conn = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_APPROVE);
			ppst.setInt(1, eventId);
	
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
