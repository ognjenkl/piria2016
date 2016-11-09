package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dto.ActorDTO;
import dto.MovieDTO;

/**
 * @author ognjen
 *
 */
public class MovieDAO {

	static List<MovieDTO> list = null;
	
	static final String SQL_INSERT = "INSERT INTO movies (name) VALUES (?)";
	static final String SQL_SELECT_ALL_ACTORS = "SELECT * FROM zndfilm.actors;";
	static final String SQL_INSERT_ACTOR = "INSERT INTO actors (name) VALUES (?)";

	public static List<MovieDTO> search(String keyWorkd){
		
		if(list == null){
			list= new ArrayList<>();
			list.add(new MovieDTO("The Fast and the Furious", new GregorianCalendar(2001, 06, 22), 
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
			list.add(new MovieDTO("The Matrix", new GregorianCalendar(1999, 03, 31),
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
			list.add(new MovieDTO("Ko to tamo peva", new GregorianCalendar(1980, 01, 01),
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
	
	public static MovieDTO getMovie(String title){
		for(MovieDTO m : list)
			if(m.getTitle().equals(title))
				return m;
		return null;
	}

	public static boolean insertMovie(MovieDTO movie){
		Connection conn = null;
		PreparedStatement ppst = null;
		boolean retVal = false;
		
		try{
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(SQL_INSERT);
			ppst.setString(1, movie.getTitle());
			
			if (ppst.executeUpdate() > 0)
				retVal = true;
		
			ppst.close();
			return retVal;
		} catch (Exception e){
			//to do log
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
	
	
	


}
