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
		userTemp.setUsername("a");
		userTemp.setPassword("a");
		userTemp.setEmail("ognjenkl@gmail.com");
		userTemp.setPrivilege(1);
		usersMap.put(userTemp.getUsername(),userTemp);

		userTemp = new UserDTO();
		userTemp.setUsername("s");
		userTemp.setPassword("s");
		userTemp.setEmail("ognjenkl@gmail.com");
		userTemp.setPrivilege(2);
		usersMap.put(userTemp.getUsername(),userTemp);

		userTemp = new UserDTO();
		userTemp.setUsername("u");
		userTemp.setPassword("u");
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
	
	public static boolean register(UserDTO userRegister){
		userRegister.setPrivilege(3);
		usersMap.put(userRegister.getUsername(), userRegister);
		
		//todo admin must allow registration
		return true;
	}
	
	public static UserDTO getUser(String username){
		return usersMap.get(username);
	}
	
	public static Map<String, UserDTO> getAllUsersMap(){
		return usersMap;
	}
}
