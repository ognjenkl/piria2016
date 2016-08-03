package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dto.MovieDTO;

/**
 * @author ognjen
 *
 */
public class MovieDAO {

	public static List<MovieDTO> search(String keyWorkd){
		List<MovieDTO> list = new ArrayList<>();
		list.add(new MovieDTO("Fast and Furious", new Date(), null, null, null, null, -1, -1, null));
		list.add(new MovieDTO("The Matrix", new Date(), null, null, null, null, -1, -1, null));
		list.add(new MovieDTO("Ko to tamo peva", new Date(), null, null, null, null, -1, -1, null));

		return list;
	}

}
