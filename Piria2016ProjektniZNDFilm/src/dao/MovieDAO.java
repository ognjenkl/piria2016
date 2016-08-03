package dao;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import dto.MovieDTO;

/**
 * @author ognjen
 *
 */
public class MovieDAO {

	public static List<MovieDTO> search(String keyWorkd){
		List<MovieDTO> list = new ArrayList<>();
		list.add(new MovieDTO("Fast and Furious", new GregorianCalendar(2009, 04, 03), null, null, null, null, -1, -1, null));
		list.add(new MovieDTO("The Matrix", new GregorianCalendar(1999, 03, 31), null, null, null, null, -1, -1, null));
		list.add(new MovieDTO("Ko to tamo peva", new GregorianCalendar(1980, 01, 01), null, null, null, null, -1, -1, null));

		return list;
	}

}
