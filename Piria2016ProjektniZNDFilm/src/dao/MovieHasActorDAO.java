package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

import dto.MovieDTO;

public class MovieHasActorDAO {

	static final String SQL_INSERT = "INSERT INTO movies_has_actors (movies_id, actors_id) VALUES (?, ?);";

	
	public static int insert(int movieId, int actorId){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT);
			ppst.setInt(1, movieId);
			ppst.setInt(2, actorId);
			
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

}
