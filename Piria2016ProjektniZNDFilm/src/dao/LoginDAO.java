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
		usersMap.put(username, user);
		
		return true;
	}
}
