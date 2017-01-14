package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.GalleryPictureDTO;

public class GalleryPictureDAO {

	private static final String SQL_GET_ALL = "SELECT * FROM gallery_pictures;";
	private static final String SQL_INSERT = "INSERT INTO gallery_pictures (name, location) VALUES (?,?);";

	
	public static List<GalleryPictureDTO> getAll() {
	List<GalleryPictureDTO> retVal = new ArrayList<>();
	Connection conn = null;
	PreparedStatement ppst = null;
	ResultSet resultSet = null;
	
	try {
		conn = ConnectionPool.getConnectionPool().checkOut();
		ppst = conn.prepareStatement(SQL_GET_ALL);

		resultSet = ppst.executeQuery();
		
		while (resultSet.next()){
			GalleryPictureDTO gp = new GalleryPictureDTO();
			gp.setId(resultSet.getInt(1));
			gp.setName(resultSet.getString(2));
			gp.setLocation(resultSet.getString(3));
			retVal.add(gp);
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
	
	public static Integer insert(GalleryPictureDTO galleryPicture) {
		Integer retVal = null;
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ppst.setString(1, galleryPicture.getName());
			ppst.setString(2, galleryPicture.getLocation());
	
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
