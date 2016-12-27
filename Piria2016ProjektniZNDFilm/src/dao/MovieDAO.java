package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import dto.ActorDTO;
import dto.MovieDTO;

/**
 * @author ognjen
 *
 */
public class MovieDAO {

	static List<MovieDTO> list = null;
	
	static final String SQL_INSERT = "INSERT INTO movies (title, release_date, storyline, trailer_location, runtime_minutes, movie_location) VALUES (?, ?, ?, ?, ?, ?);";
	static final String SQL_SELECT_ALL_ACTORS = "SELECT * FROM zndfilm.actors;";
	static final String SQL_INSERT_ACTOR = "INSERT INTO actors (name) VALUES (?);";

	public static List<MovieDTO> search(String keyWorkd){
		
		if(list == null){
			list= new ArrayList<>();
			list.add(new MovieDTO("The Fast and the Furious", Date.from(new GregorianCalendar(2001, 06, 22).toInstant()), 
					Arrays.asList(new String[]{ 
							"Vin Diesel", 
							"Paul Walker",
							"Michelle Rodriguez",
							"Jordana Brewster",
							"Rick Yune",
							"Chad Lindberg",
							"Johnny Strong",
							"Ted Levine",
							"Matt Schulze"}),
					"Los Angeles street racer Dominic Toretto falls under the suspicion of the LAPD as a string of high-speed electronics truck robberies rocks the area. Brian O'Connor, an officer of the LAPD, joins the ranks of Toretto's highly skilled racing crew undercover to convict Toretto. However, O'Connor finds himself both enamored with this new world and in love with Toretto's sister, Mia. As a rival racing crew gains strength, O'Connor must decide where his loyalties really lie.", 
					Arrays.asList(new String[]{ "Action", "Crime", "Thriller"}), 
					"https://www.youtube.com/watch?v=ZsJz2TJAPjw", 
					106, -1, null));
			list.add(new MovieDTO("The Matrix", Date.from(new GregorianCalendar(1999, 03, 31).toInstant()),
					Arrays.asList(new String[]{ 
							"Keanu Reeves",
							"Laurence Fishburne",
							"Carrie-Anne Moss",
							"Hugo Weaving",
							"Joe Pantoliano"}),
					"Thomas A. Anderson is a man living two lives. By day he is an average computer programmer and by night a hacker known as Neo. Neo has always questioned his reality, but the truth is far beyond his imagination. Neo finds himself targeted by the police when he is contacted by Morpheus, a legendary computer hacker branded a terrorist by the government. Morpheus awakens Neo to the real world, a ravaged wasteland where most of humanity have been captured by a race of machines that live off of the humans' body heat and electrochemical energy and who imprison their minds within an artificial reality known as the Matrix. As a rebel against the machines, Neo must return to the Matrix and confront the agents: super-powerful computer programs devoted to snuffing out Neo and the entire human rebellion.",
					Arrays.asList(new String[]{ "Action", "Sci-Fi"}), 
					"https://www.youtube.com/watch?v=m8e-FF8MsqU", 
					136, -1, null));
			list.add(new MovieDTO("Ko to tamo peva", Date.from(new GregorianCalendar(1980, 01, 01).toInstant()),
					Arrays.asList(new String[]{ 
							"Pavle Vujisić",
							"Dragan Nikolić",
							"Bata Stojković",
							"Aleksandar Berček",
							"Mića Tomić",
							"Taško Načić",
							"Boro Stjepanović",
							"Slavko Štimac",
							"Neda Arnerić"}),
					"On April 5, 1941, a date Serbs will recognize, men on a country road board Krstic's bus for Belgrade: two Gypsies who occasionally sing about misery, an aging war vet, a Nazi sympathizer, a dapper singer, a consumptive, and a man with a shotgun. Krstic is a world-weary cynic, out for a buck; the driver is his son, the simple, cheerful Misko. En route they pick up a priest and young newlyweds going to the seaside. Along the way, mis-adventure strikes: a flat tire, a rickety bridge, a farmer who's plowed the road, a funeral, two feuding families, an army detail, and a lost wallet slow the bus and expose rifts among the travelers. On April 6, amid rumors of war, they reach Belgrade...",
					Arrays.asList(new String[]{ "Adventure", "Comedy", "Drama"}), 
					null, 
					83, -1, 
					"https://www.youtube.com/watch?v=ZwozSLas8DM"));
		}

		return list;
	}
	
	
	public static MovieDTO getByTitle(String title){
		for(MovieDTO m : list)
			if(m.getTitle().equals(title))
				return m;
		return null;
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
			if (movie.getTrailerLocation() != null)
				ppst.setString(4, movie.getTrailerLocation());
			else
				ppst.setNull(4, Types.NULL);
			if (movie.getRuntimeMinutes() != null)
				ppst.setInt(5, movie.getRuntimeMinutes());
			else
				ppst.setNull(5, Types.NULL);
			if (movie.getMovieLocation() != null)
				ppst.setString(6, movie.getMovieLocation());
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
	
	public static Map<Integer, ActorDTO> getAllActorsMap(){
		Connection conn = null;
		ResultSet rs = null;
		Map<Integer, ActorDTO> actors = new TreeMap<>();
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_SELECT_ALL_ACTORS);
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
			ppst = conn.prepareStatement(SQL_SELECT_ALL_ACTORS);
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
