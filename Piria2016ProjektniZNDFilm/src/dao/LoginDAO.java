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
		userTemp.setFirstName("Administrator");
		userTemp.setLastName("Admin");
		userTemp.setSocialNo("1234");
		userTemp.setEmail("ognjenkl@gmail.com");
		userTemp.setPrivilege(1);
		userTemp.setPicture("n/a");
		userTemp.setUsername("a");
		userTemp.setPassword("a");
		usersMap.put(userTemp.getUsername(),userTemp);

		userTemp = new UserDTO();
		userTemp.setFirstName("Superuser");
		userTemp.setLastName("Super");
		userTemp.setSocialNo("2222");
		userTemp.setEmail("ognjenkl@gmail.com");
		userTemp.setPrivilege(2);
		userTemp.setPicture("n/a");
		userTemp.setUsername("s");
		userTemp.setPassword("s");
		usersMap.put(userTemp.getUsername(),userTemp);

		userTemp = new UserDTO();
		userTemp.setFirstName("User");
		userTemp.setLastName("User");
		userTemp.setSocialNo("3333");
		userTemp.setEmail("ognjenkl@gmail.com");
		userTemp.setPrivilege(3);
		userTemp.setPicture("n/a");
		userTemp.setUsername("u");
		userTemp.setPassword("u");
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
	
	public static boolean updateUser(UserDTO user){
		usersMap.put(user.getUsername(), user);
		return true;
	}
	
}
