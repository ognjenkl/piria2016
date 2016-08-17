package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	public static List<UserDTO> getAllUsersList(){
		List<UserDTO> list = new ArrayList<UserDTO>(usersMap.values());
		
		return list;
	}
	
	/**
	 * Universal update user method. If some argument is not to be set, should be set to <code>null</code>. 
	 * Privilege argument should be cast to String. 
	 * 
	 * @param firstName
	 * @param lastName
	 * @param socialNo
	 * @param email
	 * @param picture
	 * @param newUsername
	 * @param password
	 * @param privilege
	 * @return
	 */
	public static boolean updateUser(String username, String firstName, String lastName, String socialNo, String email, String picture, String newUsername,
			String password, String privilege) {
		
		if(username != null && !username.equals("")){
			UserDTO userToUpdate = usersMap.get(username);
		
			if(firstName != null)
				userToUpdate.setFirstName(firstName);
			if(lastName != null)
				userToUpdate.setLastName(lastName);
			if(socialNo != null)
				userToUpdate.setSocialNo(socialNo);
			if(email != null)
				userToUpdate.setEmail(email);
			if(picture != null)
				userToUpdate.setPicture(picture);
			if(newUsername != null)
				userToUpdate.setUsername(newUsername);
			if(password != null)
				userToUpdate.setPassword(password);
			if(privilege != null)
				userToUpdate.setPrivilege(Integer.valueOf(privilege));
		
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean updateUser(UserDTO user){
		return updateUser(user.getUsername(), user.getFirstName(), user.getLastName(), user.getSocialNo(), user.getEmail(), user.getPicture(), user.getUsername(), user.getPassword(), new Integer(user.getPrivilege()).toString());	
	}
	
	public static boolean updateUserWithoutPasswordAndPrivilege(UserDTO user){
		return updateUser(user.getUsername(), user.getFirstName(), user.getLastName(), user.getSocialNo(), user.getEmail(), user.getPicture(), null, null, null);
	}
	
	public static boolean updateUserWithoutPrivilege(UserDTO user){
		return updateUser(user.getUsername(), user.getFirstName(), user.getLastName(), user.getSocialNo(), user.getEmail(), user.getPicture(), null, user.getPassword(), null);
	}
	
}
