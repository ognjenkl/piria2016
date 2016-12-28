package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MovieHasGenreDAO {

	static final String SQL_INSERT = "INSERT INTO movies_has_genres (movies_id, genres_id) VALUES (?, ?);";

	public static int insert(int movieId, int genreId){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT);
			ppst.setInt(1, movieId);
			ppst.setInt(2, genreId);
			
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

