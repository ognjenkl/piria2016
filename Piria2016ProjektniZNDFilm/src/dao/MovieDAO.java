package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import dto.ActorDTO;
import dto.GenreDTO;
import dto.MovieDTO;
import dto.MovieTheMostAddedToFavoriteDTO;
import dto.ReportDTO;

/**
 * @author ognjen
 *
 */
public class MovieDAO {

	static List<MovieDTO> list = null;

	private static final String SQL_GET_ALL = "SELECT * FROM movies WHERE active=1";
	private static final String SQL_GET_ALL_BY_TITLE = "SELECT * FROM movies WHERE active=1 AND title LIKE ?;";
	private static final String SQL_GET_ALL_BY_ACTOR_ID = "SELECT * FROM movies m JOIN movies_has_actors mha ON mha.movies_id = m.id WHERE m.active=1 AND mha.actors_id=?;";
	private static final String SQL_GET_ALL_BY_GENRE_ID = "SELECT * FROM movies m JOIN movies_has_genres mhg ON mhg.movies_id = m.id WHERE m.active=1 AND mhg.genres_id=?;";
	private static final String SQL_GET_BY_ID = "SELECT * FROM movies WHERE active=1 AND id=?";
	private static final String SQL_INSERT = "INSERT INTO movies (title, release_date, storyline, trailer_location_type, trailer_location, runtime_minutes) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SQL_DELETE = "UPDATE movies SET active=0 WHERE id=?";
	private static final String SQL_UPDATE = "UPDATE movies SET title=?, release_date=? , storyline=?, trailer_location_type=?, trailer_location=?, runtime_minutes=? WHERE id=?";
	private static final String SQL_GET_FIVE_BEST_RATED = "SELECT m.*, sum(uhm.rate)/count(uhm.rate) as best_rated FROM movies m JOIN users_has_movies uhm ON uhm.movies_id = m.id GROUP BY m.id ORDER BY best_rated DESC LIMIT ?;";
	private static final String SQL_GET_BEST_RATED = "SELECT m.*, sum(uhm.rate)/count(uhm.rate) as best_rated FROM movies m JOIN users_has_movies uhm ON uhm.movies_id = m.id GROUP BY m.id ORDER BY best_rated DESC;";
	private static final String SQL_GET_THE_MOST_ADDED_TO_FAVORITE = "SELECT m.*, sum(uhm.favorite) as favorite_sum FROM movies m JOIN users_has_movies uhm ON uhm.movies_id = m.id GROUP BY m.id ORDER BY sum(uhm.favorite) DESC;";
	private static final String SQL_GET_NUMBER_OF_ADDED_MOVIES_BY_MONTH = "SELECT MONTHNAME(added_date), COUNT(*) FROM movies WHERE active = 1 GROUP BY MONTH(added_date);";
	
	public static List<MovieDTO> getAll(){
		List<MovieDTO> retVal = new ArrayList<>();
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL);
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				MovieDTO movie = new MovieDTO();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setStoryline(resultSet.getString(4));
				movie.setTrailerLocationType(resultSet.getInt(5));
				movie.setTrailerLocation(resultSet.getString(6));
				movie.setRuntimeMinutes(resultSet.getInt(7));
				movie.setRate(resultSet.getDouble(8));
				
				movie.setActors(MovieHasActorDAO.getActorsByMovieId(movie.getId()));
				movie.setGenres(MovieHasGenreDAO.getGenresByMovieId(movie.getId()));
				
				retVal.add(movie);
			}
			
			ppst.close();

			if(retVal.size() > 0)
				return retVal;
			else 
				return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	public static MovieDTO getById(Integer id){
		MovieDTO retVal = null;
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_BY_ID);
			ppst.setInt(1, id);
			resultSet = ppst.executeQuery();
			
			if (resultSet.next()){
				MovieDTO movie = new MovieDTO();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setStoryline(resultSet.getString(4));
				movie.setTrailerLocationType(resultSet.getInt(5));
				movie.setTrailerLocation(resultSet.getString(6));
				movie.setRuntimeMinutes(resultSet.getInt(7));
				movie.setRate(resultSet.getDouble(8));
				
				movie.setActors(MovieHasActorDAO.getActorsByMovieId(movie.getId()));
				movie.setGenres(MovieHasGenreDAO.getGenresByMovieId(movie.getId()));
				
				retVal = movie;
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
	
	public static List<MovieDTO> getAllByTitleLike(String searchText){
		List<MovieDTO> movieList = new ArrayList<>();
		
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_BY_TITLE);
			ppst.setString(1, "%" + searchText + "%");
			resultSet = ppst.executeQuery();
			
			while(resultSet.next()){
				MovieDTO movie = new MovieDTO();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setStoryline(resultSet.getString(4));
				movie.setTrailerLocationType(resultSet.getInt(5));
				movie.setTrailerLocation(resultSet.getString(6));
				movie.setRuntimeMinutes(resultSet.getInt(7));
				movie.setRate(resultSet.getDouble(8));
				
				movie.setActors(MovieHasActorDAO.getActorsByMovieId(movie.getId()));
				movie.setGenres(MovieHasGenreDAO.getGenresByMovieId(movie.getId()));
				
				movieList.add(movie);
			}
			
			ppst.close();

			if(movieList.size() > 0)
				return movieList;
			else 
				return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}

	public static List<MovieDTO> getAllByActor(ActorDTO actorDTO){
		List<MovieDTO> movieList = new ArrayList<>();
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_BY_ACTOR_ID);
			ppst.setInt(1, actorDTO.getId());
			resultSet = ppst.executeQuery();
			
			while(resultSet.next()){
				MovieDTO movie = new MovieDTO();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setStoryline(resultSet.getString(4));
				movie.setTrailerLocationType(resultSet.getInt(5));
				movie.setTrailerLocation(resultSet.getString(6));
				movie.setRuntimeMinutes(resultSet.getInt(7));
				movie.setRate(resultSet.getDouble(8));
				
				movie.setActors(MovieHasActorDAO.getActorsByMovieId(movie.getId()));
				movie.setGenres(MovieHasGenreDAO.getGenresByMovieId(movie.getId()));
				
				movieList.add(movie);
			}
			
			ppst.close();

			if(movieList.size() > 0)
				return movieList;
			else 
				return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	public static List<MovieDTO> getAllByGenre(GenreDTO genreDTO){
		List<MovieDTO> movieList = new ArrayList<>();
		
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_ALL_BY_GENRE_ID);
			ppst.setInt(1, genreDTO.getId());
			resultSet = ppst.executeQuery();
			
			while(resultSet.next()){
				MovieDTO movie = new MovieDTO();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setStoryline(resultSet.getString(4));
				movie.setTrailerLocationType(resultSet.getInt(5));
				movie.setTrailerLocation(resultSet.getString(6));
				movie.setRuntimeMinutes(resultSet.getInt(7));
				movie.setRate(resultSet.getDouble(8));
				
				movie.setActors(MovieHasActorDAO.getActorsByMovieId(movie.getId()));
				movie.setGenres(MovieHasGenreDAO.getGenresByMovieId(movie.getId()));
				
				movieList.add(movie);
			}
			
			ppst.close();

			if(movieList.size() > 0)
				return movieList;
			else 
				return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}

	public static int insert(MovieDTO movie){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		ResultSet resultSet = null;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ppst.setString(1, movie.getTitle());
			if (movie.getReleaseDate() != null)
				ppst.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
			else
				ppst.setNull(2, Types.NULL);
			if (movie.getStoryline() != null)
				ppst.setString(3, movie.getStoryline());
			else
				ppst.setNull(3, Types.NULL);
			if (movie.getTrailerLocationType() != null)
				ppst.setInt(4, movie.getTrailerLocationType());
			else
				ppst.setNull(4, Types.NULL);
			if (movie.getTrailerLocation() != null)
				ppst.setString(5, movie.getTrailerLocation());
			else
				ppst.setNull(5, Types.NULL);
			if (movie.getRuntimeMinutes() != null)
				ppst.setInt(6, movie.getRuntimeMinutes());
			else
				ppst.setNull(6, Types.NULL);
			
			
			int rowCount = ppst.executeUpdate();
			resultSet = ppst.getGeneratedKeys();
			
			if (rowCount > 0 && resultSet.next())
				retVal = resultSet.getInt(1);
			
			ppst.close();
			return retVal;
		} catch (Exception e){
			//TODO log
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	

	public static int delete(MovieDTO movie){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_DELETE);
			ppst.setInt(1, movie.getId());
			
			retVal = ppst.executeUpdate();
			
			
			ppst.close();
			return retVal;
		} catch (Exception e){
			//TODO log
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}
	
	
	public static int update(MovieDTO movie){
		Connection conn = null;
		PreparedStatement ppst = null;
		int retVal = -1;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_UPDATE);
			ppst.setString(1, movie.getTitle());
			ppst.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
			ppst.setString(3, movie.getStoryline());
			ppst.setInt(4, movie.getTrailerLocationType());
			ppst.setString(5, movie.getTrailerLocation());
			ppst.setInt(6, movie.getRuntimeMinutes());
			ppst.setInt(7, movie.getId());
			
			retVal = ppst.executeUpdate();
			
			
			ppst.close();
			return retVal;
		} catch (Exception e){
			//TODO log
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	}

	
	/**
	 * 
	 * @param limit how many best rated to return, null is to retrun all
	 * @return
	 */
	public static List<MovieDTO> getBestRated(Integer limit) {
		List<MovieDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			
			if(limit != null){
				ppst = conn.prepareStatement(SQL_GET_FIVE_BEST_RATED);
				ppst.setInt(1, limit);
			} else 
				ppst = conn.prepareStatement(SQL_GET_BEST_RATED);
					
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				MovieDTO movie = new MovieDTO();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setStoryline(resultSet.getString(4));
				movie.setTrailerLocationType(resultSet.getInt(5));
				movie.setTrailerLocation(resultSet.getString(6));
				movie.setRuntimeMinutes(resultSet.getInt(7));
				
				if (resultSet.getDouble(11) > 0)
					movie.setRate(resultSet.getDouble(11));
				else
					continue;
				
				movie.setActors(MovieHasActorDAO.getActorsByMovieId(movie.getId()));
				movie.setGenres(MovieHasGenreDAO.getGenresByMovieId(movie.getId()));
				
				retVal.add(movie);
			}
			
			ppst.close();
			
			if (retVal.size() > 0) 
				return retVal;
			else
				return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
		
	}
	
	
	/**
	 * 
	 * @param limit how many best rated to return, null is to retrun all
	 * @return
	 */
	public static List<MovieTheMostAddedToFavoriteDTO> getMostAddedToFavorite() {
		List<MovieTheMostAddedToFavoriteDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			
			ppst = conn.prepareStatement(SQL_GET_THE_MOST_ADDED_TO_FAVORITE);
					
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				MovieTheMostAddedToFavoriteDTO movie = new MovieTheMostAddedToFavoriteDTO();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setStoryline(resultSet.getString(4));
				movie.setTrailerLocationType(resultSet.getInt(5));
				movie.setTrailerLocation(resultSet.getString(6));
				movie.setRuntimeMinutes(resultSet.getInt(7));
				
				if (resultSet.getInt(11) > 0)
					movie.setAddedToFavoriteSum(resultSet.getInt(11));
				else
					continue;
				
				movie.setActors(MovieHasActorDAO.getActorsByMovieId(movie.getId()));
				movie.setGenres(MovieHasGenreDAO.getGenresByMovieId(movie.getId()));
				
				retVal.add(movie);
			}
			
			ppst.close();
			
			if (retVal.size() > 0) 
				return retVal;
			else
				return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
		
	}
	
	
	public static List<ReportDTO> getNumberOfAddedMoviesByMonthForReport() {
		List<ReportDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_NUMBER_OF_ADDED_MOVIES_BY_MONTH);
	
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				ReportDTO reportDTO = new ReportDTO();
				reportDTO.setName(resultSet.getString(1));
				reportDTO.setValue(resultSet.getDouble(2));
				retVal.add(reportDTO);
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
	
	public static List<ReportDTO> getBestRatedMoviesForReport() {
		List<ReportDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_BEST_RATED);
	
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				ReportDTO reportDTO = new ReportDTO();
				reportDTO.setName(resultSet.getString("title"));
				reportDTO.setValue(resultSet.getDouble("best_rated"));
				retVal.add(reportDTO);
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
	

	public static List<ReportDTO> getTheMostAddedToFavoriteForReport() {
		List<ReportDTO> retVal = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_GET_THE_MOST_ADDED_TO_FAVORITE);
	
			resultSet = ppst.executeQuery();
			
			while (resultSet.next()){
				ReportDTO reportDTO = new ReportDTO();
				reportDTO.setName(resultSet.getString("title"));
				reportDTO.setValue(resultSet.getDouble("favorite_sum"));
				retVal.add(reportDTO);
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
