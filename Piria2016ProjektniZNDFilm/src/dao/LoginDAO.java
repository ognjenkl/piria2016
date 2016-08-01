package dao;

import java.util.HashMap;
import java.util.Map;

import dto.UserDTO;

/**
 * @author ognjen
 *
 */
public class LoginDAO {

	static Map <String, UserDTO> usersMap = new HashMap<>();
	static{
		UserDTO userTemp = null;
		
		userTemp = new UserDTO();
		userTemp.setUsername("admin");
		userTemp.setPassword("admin");
		userTemp.setEmail("ognjenkl@gmail.com");
		userTemp.setPrivilege(1);
		usersMap.put(userTemp.getUsername(),userTemp);

		userTemp = new UserDTO();
		userTemp.setUsername("superuser");
		userTemp.setPassword("superuser");
		userTemp.setEmail("ognjenkl@gmail.com");
		userTemp.setPrivilege(2);
		usersMap.put(userTemp.getUsername(),userTemp);

		userTemp = new UserDTO();
		userTemp.setUsername("ogi");
		userTemp.setPassword("ogi");
		userTemp.setEmail("ognjenkl@gmail.com");
		userTemp.setPrivilege(3);
		usersMap.put(userTemp.getUsername(),userTemp);

	}
	
	public static UserDTO login(String username, String password){
		UserDTO user = usersMap.get(username);
		if(user != null && user.getPassword().equals(password) && user.getUsername().equals(username))
			return user;
		else
			return null;

	}
	
	public static boolean register(String username, String password, String email){
		UserDTO user = null;
		user = new UserDTO();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setPrivilege(3);
		usersMap.put(username, user);
		
		return true;
	}
}
